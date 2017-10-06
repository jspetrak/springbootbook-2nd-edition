package de.codeboje.springbootbook.springcoreexercise

// Constructor params storing omitted, not needed for explanation
class RemoteSpamDetector(url : String, username : String, password : String) : SpamDetector {

	override fun containsSpam(value : String) : Boolean {
		// make the remote call
		return false
	}

}
