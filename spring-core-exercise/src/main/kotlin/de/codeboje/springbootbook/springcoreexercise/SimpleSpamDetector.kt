package de.codeboje.springbootbook.springcoreexercise

import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

@Service()
class SimpleSpamDetector : SpamDetector {

	private val spamWords : MutableList<String>

	constructor(filename : String) {
		this.spamWords = Files.readAllLines(File(filename).toPath())
	}

	override fun containsSpam(value : String) : Boolean =
		spamWords.any { spam : String -> value.toLowerCase().contains(spam) }

}