package de.codeboje.springbootbook.commentstore.restapi

import de.codeboje.springbootbook.commentstore.service.CommentService
import de.codeboje.springbootbook.model.Comment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.CounterService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.Locale
import javax.servlet.http.HttpServletResponse

@Controller()
open class WriteController @Autowired() constructor(private val commentService : CommentService, private val counterService : CounterService) {

	companion object {
		@JvmStatic() private val LOGGER : Logger by lazy { LoggerFactory.getLogger(WriteController::class.java) }
	}

	@RequestMapping(value = "/comments", method = arrayOf(RequestMethod.POST))
	@ResponseStatus(value = HttpStatus.CREATED)
	@ResponseBody
	fun create(@ModelAttribute model : Comment) : String {

		counterService.increment("commentstore.post")

		LOGGER.info("form post started")

		val id = commentService.put(model)

		LOGGER.info("form post done")

		return id
	}

	@RequestMapping(value = "/comment/{id}", method = arrayOf(RequestMethod.DELETE))
	@ResponseStatus(value = HttpStatus.OK)
	fun delete(@PathVariable(value = "id") id : String, response : HttpServletResponse) {
		commentService.delete(id)
	}

	@ExceptionHandler(EmptyResultDataAccessException::class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	fun handle404(ex : Exception, locale : Locale) {
		LOGGER.debug("Resource not found {}", ex.message)
	}

}