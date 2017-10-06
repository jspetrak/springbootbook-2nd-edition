package de.codeboje.springbootbook.springcoreexercise

import org.springframework.context.annotation.AnnotationConfigApplicationContext

object SpamCheckerApplication {

	@JvmStatic() fun main(args : Array<String>) {
		val context = AnnotationConfigApplicationContext(SpamAppConfig::class.java)
		context.getBean(ControlFlow::class.java).run(args)
	}

}
