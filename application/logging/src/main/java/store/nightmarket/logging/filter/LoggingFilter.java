package store.nightmarket.logging.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import net.logstash.logback.argument.StructuredArguments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class LoggingFilter extends OncePerRequestFilter {

	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		ContentCachingRequestWrapper wrappedRequest = (ContentCachingRequestWrapper)request;
		ContentCachingResponseWrapper wrappedResponse = (ContentCachingResponseWrapper)response;

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			logRequest(wrappedRequest);
			logResponse(wrappedResponse);
		}

	}

	private void logRequest(ContentCachingRequestWrapper request) throws IOException {
		if (request == null)
			return;

		String body = new String(request.getContentAsByteArray(), request.getCharacterEncoding());

		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("method", request.getMethod());
		requestMap.put("uri", request.getRequestURI());
		requestMap.put("headers", Collections.list(request.getHeaderNames())
			.stream().collect(Collectors.toMap(h -> h, request::getHeader)));

		if (!body.isEmpty()) {
			try {
				JsonNode jsonBody = objectMapper.readTree(body);
				requestMap.put("body", jsonBody);
			} catch (Exception e) {
				requestMap.put("body", body);
			}
		} else {
			requestMap.put("body", null);
		}

		log.info(">>> REQUEST", StructuredArguments.value("request", requestMap));
	}

	private void logResponse(ContentCachingResponseWrapper response) throws IOException {
		if (response == null)
			return;

		String body = new String(response.getContentAsByteArray(), response.getCharacterEncoding());

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("status", response.getStatus());
		responseMap.put("headers", response.getHeaderNames()
			.stream().collect(Collectors.toMap(h -> h, response::getHeader)));

		if (!body.isEmpty()) {
			try {
				JsonNode jsonBody = objectMapper.readTree(body);
				responseMap.put("body", jsonBody);
			} catch (Exception e) {
				responseMap.put("body", body);
			}
		} else {
			responseMap.put("body", null);
		}

		log.info("<<< RESPONSE", StructuredArguments.value("response", responseMap));
	}

}
