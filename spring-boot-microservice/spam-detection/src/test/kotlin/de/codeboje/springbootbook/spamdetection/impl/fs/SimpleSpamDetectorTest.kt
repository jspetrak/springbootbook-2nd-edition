package de.codeboje.springbootbook.spamdetection.impl.fs

import de.codeboje.springbootbook.spamdetection.SpamDetector
import de.codeboje.springbootbook.spamdetection.impl.SimpleSpamDetector
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests with sample words")
class SimpleSpamDetectorTest {

	private lateinit var detector : SpamDetector

	@BeforeEach()
	fun setupDetector() {
		this.detector = SimpleSpamDetector("src/test/resources/words.spam")
	}

	@Test()
	@DisplayName("Detector identifies true spam")
	fun trueSpam() {
		assertTrue(detector.containsSpam("I LOVE VIAGRA"))
		assertTrue(detector.containsSpam("Horst Fuck"))
		assertTrue(detector.containsSpam("Hort@horst-porn.com"))
	}

	@Test()
	@DisplayName("Detector ignores legal values")
	fun validValues() {
		assertFalse(detector.containsSpam("I LOVE Dogs"))
		assertFalse(detector.containsSpam("I LOVE Robots"))
		assertFalse(detector.containsSpam("I LOVE Cats"))
	}

}
