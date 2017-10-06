package de.codeboje.springbootbook.commentstore.restapi

import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import de.codeboje.springbootbook.commentstore.service.CommentService
import de.codeboje.springbootbook.model.Comment
import de.codeboje.springbootbook.spamdetection.SpamDetector
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.ArrayList

/**
 * Test for the {@link ReadController} using a mock test approach.
 */
@RunWith(SpringRunner::class)
@WebMvcTest(ReadController::class)
@AutoConfigureMockMvc()
@MockBean(SpamDetector::class, CounterService::class)
open class ReadControllerTests {

	@MockBean()
	private lateinit var commentService : CommentService

	@Autowired()
	private lateinit var mvc : MockMvc

	@Test()
	fun testGetComments() {
		val model = setupDummyModel()
		val mockReturn = ArrayList<Comment>()
		mockReturn.add(model)

		`when`(this.commentService.list(anyString())).thenReturn(mockReturn)

		this.mvc.perform(get("/comments/" + model.pageId!!))
			.andExpect(status().`is`(200))
			.andExpect(jsonPath("$[0].id", `is`<String>(model.id)))
			.andExpect(
				jsonPath("$[0].creationDate", `is`<String>(DateTimeFormatter.ISO_INSTANT.format(model
					.creationDate!!))))
			.andExpect(jsonPath("$[0].username", `is`<String>(model.username)))
			.andExpect(jsonPath("$[0].comment", `is`<String>(model.comment)))
			.andExpect(
				jsonPath("$[0].emailAddress",
					`is`<String>(model.emailAddress)))

		verify<CommentService>(this.commentService, times(1)).list(anyString())
	}

	@Test()
	fun testGetSpamComments() {
		val model = setupDummyModel()
		val mockReturn = ArrayList<Comment>()
		mockReturn.add(model)

		`when`(this.commentService.list(anyString())).thenReturn(mockReturn)

		this.mvc.perform(get("/comments/" + model.pageId + "/spam"))
			.andExpect(status().`is`(200))
			.andExpect(jsonPath("$", hasSize<Any>(0)))
	}

	@Test()
	fun testGetCommentsNotFound() {
		this.mvc.perform(get("/comments/2")).andExpect(status().`is`(404))
	}

	private fun setupDummyModel() : Comment =
		Comment(
			username = "testuser",
			id = "dqe345e456rf34rw",
			pageId = "product0815",
			emailAddress = "example@example.com",
			comment = "I am the comment",
			creationDate = Instant.now()
		)

}
