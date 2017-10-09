package de.codeboje.springbootbook.springcoreexercise

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component()
open class ControlFlow constructor(@Qualifier("simpleSpamDetector") private val detector : SpamDetector) {

	fun run(args : Array<String>) {
		println(detector.containsSpam(args[0]))
	}

}
