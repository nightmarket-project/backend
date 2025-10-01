package store.nightmarket.logging.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		ContentCachingRequestWrapper wrappedRequest = WebUtils.getNativeRequest(request,
			ContentCachingRequestWrapper.class);
		ContentCachingResponseWrapper wrappedResponse = WebUtils.getNativeResponse(response,
			ContentCachingResponseWrapper.class);

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			logRequest(wrappedRequest);
			if (wrappedResponse != null) {
				wrappedResponse.copyBodyToResponse();
			}
			logResponse(wrappedResponse);
		}

	}

	private void logRequest(ContentCachingRequestWrapper request) throws UnsupportedEncodingException {
		if (request == null) {
			return;
		}

		String body = new String(request.getContentAsByteArray(), request.getCharacterEncoding());

		log.info(">>> REQUEST: {} {} Headers: {} Body: {}",
			request.getMethod(),
			request.getRequestURI(),
			Collections.list(request.getHeaderNames())
				.stream()
				.collect(Collectors.toMap(h -> h, request::getHeader)),
			body);
	}

	private void logResponse(ContentCachingResponseWrapper response) throws IOException {
		if (response == null) {
			return;
		}

		String body = new String(response.getContentAsByteArray(), response.getCharacterEncoding());

		log.info("<<< RESPONSE: Status={} Headers: {} Body: {}",
			response.getStatus(),
			response.getHeaderNames()
				.stream()
				.collect(Collectors.toMap(h -> h, response::getHeader)),
			body);
	}

}
