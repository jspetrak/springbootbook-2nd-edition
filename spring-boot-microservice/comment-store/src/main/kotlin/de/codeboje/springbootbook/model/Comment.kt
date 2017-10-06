package de.codeboje.springbootbook.model

import java.io.Serializable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(
	name = "comments_model",
	indexes = arrayOf(Index(name = "idx_pageId", columnList = "pageId"))
)
data class Comment(
	@Id()
	@Column(length = 36)
	var id : String = "",

	@Version
	var version : Int? = null,

	var lastModificationDate : Instant? = null,

	var creationDate : Instant? = null,

	@Column(length = 32)
	var pageId : String? = null,

	@Column(length = 32)
	var username : String? = null,

	@Column(length = 32)
	var emailAddress : String? = null,

	@Column
	var spam : Boolean = false,

	@Column(columnDefinition = "TEXT")
	var comment : String? = null
) : Serializable {

	companion object {
		private const val serialVersionUID : Long = 8926987149780391093L
	}

}
