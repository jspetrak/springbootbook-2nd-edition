package de.codeboje.springbootbook.commentstore.restapi

import de.codeboje.springbootbook.commentstore.service.CommentService
import de.codeboje.springbootbook.model.Comment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.io.FileNotFoundException
import java.util.Locale

@RestController()
open class ReadController @Autowired() constructor(private val commentService : CommentService, private val counterService : CounterService) {

	companion object {
		@JvmStatic() private val LOGGER : Logger by lazy { LoggerFactory.getLogger(ReadController::class.java) }
	}

	@RequestMapping(value = "/comments/{id}")
	fun getComments(@PathVariable(value = "id") pageId : String) : List<Comment> {
		LOGGER.info("get comments for pageId {}", pageId)

		counterService.increment("commentstore.list_comments")

		val r = commentService.list(pageId)
		if (r.isEmpty()) {
			LOGGER.info("get comments for pageId {} - not found", pageId)
			throw FileNotFoundException("/list/" + pageId)
		}

		LOGGER.info("get comments for pageId {} - done", pageId)
		return r
	}

	@RequestMapping(value = "/comments/{id}/paging")
	fun getCommentsPaging(@PathVariable(value = "id") pageId : String, pageable : Pageable) : Page<Comment> =
		commentService.list(pageId, pageable)

	@RequestMapping(value = "/comments/{id}/spam")
	fun getSpamComments(@PathVariable(value = "id") pageId : String) : List<Comment> {
		LOGGER.info("get spam comments for pageId {}", pageId)
		counterService.increment("commentstore.list_comments")
		val r = commentService.listSpamComments(pageId)
		LOGGER.info("get spam comments for pageId {} - done", pageId)
		return r
	}

	@ExceptionHandler(FileNotFoundException::class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	fun handle404(ex : Exception, locale : Locale) {
		LOGGER.debug("Resource not found {}", ex.message)
	}

}
