package de.codeboje.springbootbook.springcoreexercise

import org.springframework.stereotype.Service

// Constructor params storing omitted, not needed for explanation
@Service()
open class RemoteSpamDetector(url : String, username : String, password : String) : SpamDetector {

	override fun containsSpam(value : String) : Boolean {
		// make the remote call
		return false
	}

}
