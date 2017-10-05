package de.codeboje.springbootbook.spamdetection.impl

import java.io.File
import java.nio.file.Files

import de.codeboje.springbootbook.spamdetection.SpamDetector

/**
 * Simple Spam Detector - checks for unwanted words.
 */
class SimpleSpamDetector : SpamDetector {

	private val spamWords : MutableList<String>

	constructor(filename : String) {
		this.spamWords = Files.readAllLines(File(filename).toPath())
	}

	override fun containsSpam(value : String) : Boolean =
		spamWords.any { spam : String -> value.toLowerCase().contains(spam) }

}
