package de.codeboje.springbootbook.commentstore.service

import de.codeboje.springbootbook.model.Comment
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest()
@ExtendWith(SpringExtension::class)
open class CommentstoreServiceImplTest {

	@Autowired()
	private lateinit var commentService : CommentService

	@Autowired()
	private lateinit var commentRepository : CommentRepository

	private lateinit var comment : Comment

	@BeforeEach()
	fun setup() {
		comment = Comment(
			username = "testuser",
			id = "dqe345e456rf34rw",
			pageId = "product0815",
			emailAddress = "example@example.com",
			comment = "I am the comment"
		)

		commentRepository.deleteAll()
	}

	@Test()
	fun testPutAndGet() {
		commentService.put(comment)

		val dbModel = commentService.get(comment.id)
		assertNotNull(dbModel)
		assertEquals(comment.comment, dbModel!!.comment)
		assertEquals(comment.id, dbModel.id)
		assertEquals(comment.pageId, dbModel.pageId)
		assertEquals(comment.username, dbModel.username)
		assertEquals(comment.emailAddress, dbModel.emailAddress)

		assertNotNull(dbModel.lastModificationDate)
		assertNotNull(dbModel.creationDate)
		assertFalse(comment.spam)
	}

	@Test()
	fun testListNotFound() {
		commentService.put(comment)
		val r = commentService.list("sdfgsdwerwert")
		assertTrue(r.isEmpty())
	}

	@Test()
	fun testList() {
		commentService.put(comment)
		val r = commentService.list(comment.pageId!!)
		assertNotNull(r)
		assertEquals(1, r.size)
		assertEquals(comment.id, r.get(0).id)
	}

	@Test()
	fun testListspam() {
		comment.comment = "I Love Viagra"
		commentService.put(comment)
		val r = commentService.listSpamComments(comment.pageId!!)
		assertNotNull(r)
		assertEquals(1, r.size)
		assertEquals(comment.id, r.get(0).id)
	}

}