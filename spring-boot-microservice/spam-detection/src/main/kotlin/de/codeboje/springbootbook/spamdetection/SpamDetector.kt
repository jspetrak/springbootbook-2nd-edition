package de.codeboje.springbootbook.spamdetection

/**
 * Interface for detecting spam comments
 */
interface SpamDetector {

	fun containsSpam(value : String) : Boolean

}
