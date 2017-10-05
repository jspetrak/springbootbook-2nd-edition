package de.codeboje.springbootbook.commentstore.service

import de.codeboje.springbootbook.model.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, String> {

	// @Query("select a from CommentModel a where a.pageId = ?1")
	fun findByPageId(pageId : String) : List<Comment>

	fun findByPageIdAndSpamIsTrue(pageId : String) : List<Comment>

	fun findByPageId(pageId : String, pageable : Pageable) : Page<Comment>
}
