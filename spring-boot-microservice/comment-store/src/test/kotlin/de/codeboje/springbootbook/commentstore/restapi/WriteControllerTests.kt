package de.codeboje.springbootbook.commentstore.restapi

import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import de.codeboje.springbootbook.commentstore.service.CommentService
import de.codeboje.springbootbook.model.Comment
import de.codeboje.springbootbook.spamdetection.SpamDetector
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

/**
 * Test for the {@link ReadController} using a mock test approach.
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(ReadController::class)
@AutoConfigureMockMvc()
@MockBean(SpamDetector::class, CounterService::class)
open class WriteControllerTests {

	@MockBean()
	private lateinit var commentService : CommentService

	@Autowired()
	private lateinit var mvc : MockMvc

	@Test()
	fun testPost() {
		val model = setupDummyModel()

		val result = this.mvc.perform(MockMvcRequestBuilders.post("/comments")
			.param("comment", model.comment!!)
			.param("pageId", model.pageId!!)
			.param("emailAddress", model.emailAddress!!)
			.param("username", model.username!!))
			.andExpect(status().`is`(200)).andReturn()

		val id = result.response.contentAsString
		val dbModel = commentService.get(id)
		assertNotNull(dbModel)
		assertEquals(model.comment, dbModel!!.comment)
		assertEquals(model.pageId, dbModel.pageId)
		assertEquals(model.username, dbModel.username)
		assertEquals(model.emailAddress, dbModel.emailAddress)

		assertNotNull(dbModel.lastModificationDate)
		assertNotNull(dbModel.creationDate)
		assertFalse(model.spam)
	}

	@Test()
	fun testDelete() {
		val model = setupDummyModel()

		val id = commentService.put(model)

		this.mvc.perform(delete("/" + id)).andExpect(status().isOk)

		assertNull(commentService.get(model.id))
	}

	private fun setupDummyModel() : Comment =
		Comment(
			username = "testuser",
			id = "dqe345e456rf34rw",
			pageId = "product0815",
			emailAddress = "example@example.com",
			comment = "I am the comment"
		)

}
