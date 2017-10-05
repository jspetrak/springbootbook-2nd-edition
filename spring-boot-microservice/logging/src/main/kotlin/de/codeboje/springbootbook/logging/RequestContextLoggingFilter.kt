package de.codeboje.springbootbook.logging

import java.util.UUID

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

class RequestContextLoggingFilter : Filter {

	override fun doFilter(request : ServletRequest, response : ServletResponse, chain : FilterChain) {
		try {
			var requestUUID = (request as HttpServletRequest).getHeader(SBBLoggingConstants.REQUEST_UUID_HEADER)

			if (requestUUID.isNullOrEmpty()) {
				requestUUID = createId()

				LOGGER.info("Got request without {} and assign new {}", SBBLoggingConstants.REQUEST_UUID_HEADER, requestUUID)
			}

			MDC.put(SBBLoggingConstants.REQUEST_UUID_HEADER, requestUUID)

			chain.doFilter(request, response)
		} finally {
			MDC.clear()
		}
	}

	override fun init(filterConfig : FilterConfig) {}

	override fun destroy() {}

	companion object {
		@JvmStatic() private val LOGGER : Logger by lazy { LoggerFactory.getLogger(RequestContextLoggingFilter::class.java) }

		@JvmStatic() fun createId() : String =
			UUID.randomUUID().toString().replace("-", "")
	}

}
