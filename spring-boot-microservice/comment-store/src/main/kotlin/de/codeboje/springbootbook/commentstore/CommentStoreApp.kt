package de.codeboje.springbootbook.commentstore

import de.codeboje.springbootbook.logging.RequestContextLoggingFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ImportResource
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Application Configuration and Starter for the commentstore
 *
 * @author Jens Boje
 * @author Josef Petrak
 */
@SpringBootApplication
@EnableSpringDataWebSupport
@EnableTransactionManagement
@ComponentScan(basePackages = arrayOf("de.codeboje.springbootbook"))
//@EnableJpaRepositories(basePackages= {"de.codeboje.springbootbook"})
@EntityScan(basePackages = arrayOf("de.codeboje.springbootbook"))
@ImportResource(value = *arrayOf("classpath*:legacy-context.xml"))
open class CommentStoreApp {

	/**
	 * Maps the commons logging Filter to all requests; done by spring boot
	 * @return
	 */
	@Bean() open fun initRequestContextLoggingFilter() = RequestContextLoggingFilter()

	companion object {
		@JvmStatic() fun main(args : Array<String>) {
			SpringApplication.run(CommentStoreApp::class.java, *args)
		}
	}

}
