package de.codeboje.springbootbook.commentstore.service

import de.codeboje.springbootbook.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CommentService {

	/**
	 * Saves the comment
	 * @param model
	 * @return returns the id of the model
	 */
	fun put(model : Comment) : String

	fun list(pageId : String) : List<Comment>

	fun list(pageId : String, pageable : Pageable) : Page<Comment>

	fun get(id : String): Comment?

	fun listSpamComments(pageId : String) : List<Comment>

	fun delete(id : String)

}