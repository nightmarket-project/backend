package store.nightmarket.logging.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import net.logstash.logback.argument.StructuredArguments;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.logging.model.detail.AccessEvent;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class LoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		long startTime = System.currentTimeMillis();
		ContentCachingRequestWrapper wrappedRequest = (ContentCachingRequestWrapper)request;
		ContentCachingResponseWrapper wrappedResponse = (ContentCachingResponseWrapper)response;

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			logRequest(wrappedRequest);
			logResponse(wrappedResponse, wrappedRequest, startTime);
		}

	}

	private void logRequest(ContentCachingRequestWrapper request) throws IOException {

		AccessEvent event = AccessEvent.builder()
			.eventType("HTTP")
			.direction("IN")
			.uri(request.getRequestURI())
			.method(request.getMethod())
			.headers(getHeaderMap(request))
			.body(parseBody(new String(request.getContentAsByteArray(), request.getCharacterEncoding())))
			.build();

		log.info(">>> HTTP REQUEST", StructuredArguments.value("event", event));
	}

	private void logResponse(ContentCachingResponseWrapper response, ContentCachingRequestWrapper request,
		long startTime) throws
		IOException {

		AccessEvent event = AccessEvent.builder()
			.eventType("HTTP")
			.direction("OUT")
			.uri(request.getRequestURI())
			.method(request.getMethod())
			.headers(getHeaderMap(request))
			.body(parseBody(new String(response.getContentAsByteArray(), response.getCharacterEncoding())))
			.status(response.getStatus())
			.responseTimeMs(System.currentTimeMillis() - startTime)
			.build();

		log.info(">>> HTTP RESPONSE", StructuredArguments.value("event", event));
	}

	private static Map<String, Object> getHeaderMap(ContentCachingRequestWrapper request) {
		return Collections.list(request.getHeaderNames())
			.stream()
			.collect(Collectors.toMap(h -> h, request::getHeader));
	}

	private Object parseBody(String body) {
		try {
			return new ObjectMapper().readTree(body);
		} catch (Exception e) {
			return body;
		}
	}

}
