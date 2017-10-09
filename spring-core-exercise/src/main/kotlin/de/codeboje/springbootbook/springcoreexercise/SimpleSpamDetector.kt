package de.codeboje.springbootbook.springcoreexercise

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files

@Service()
open class SimpleSpamDetector : SpamDetector {

	private val spamWords : MutableList<String>

	constructor(@Value("\${spamWordsFilename}") filename : String) {
		this.spamWords = Files.readAllLines(File(filename).toPath())
	}

	override fun containsSpam(value : String) : Boolean =
		spamWords.any { spam : String -> value.toLowerCase().contains(spam) }

}
