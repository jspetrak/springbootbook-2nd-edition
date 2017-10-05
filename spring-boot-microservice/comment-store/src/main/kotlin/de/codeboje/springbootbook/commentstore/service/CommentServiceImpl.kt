package de.codeboje.springbootbook.commentstore.service

import de.codeboje.springbootbook.model.Comment
import de.codeboje.springbootbook.spamdetection.SpamDetector
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service()
open class CommentServiceImpl @Autowired() constructor(private val detector : SpamDetector, private val repository : CommentRepository) : CommentService {

	@Transactional()
	override fun put(model : Comment) : String {
		if (model.id.isEmpty()) {
			model.id = UUID.randomUUID().toString()
		}

		model.spam =
			listOf(model.username, model.emailAddress, model.comment)
				.filterNotNull()
				.any { value -> detector.containsSpam(value) }

		val dbModel : Comment? = get(model.id)

		if (dbModel == null) {
			model.creationDate = Instant.now()
			model.lastModificationDate = Instant.now()
			repository.save(model)
		} else {
			dbModel.username = model.username
			dbModel.comment = model.comment
			dbModel.lastModificationDate = Instant.now()
			repository.save(dbModel)
		}

		return model.id
	}

	override fun get(id : String) : Comment? = repository.findById(id).orElse(null)

	override fun list(pageId : String) : List<Comment> = repository.findByPageId(pageId)

	override fun listSpamComments(pageId : String) : List<Comment> =
		repository.findByPageIdAndSpamIsTrue(pageId)

	override fun delete(id : String) {
		repository.deleteById(id)
	}

	override fun list(pageId : String, pageable : Pageable) : Page<Comment> =
		repository.findByPageId(pageId, pageable)

}
