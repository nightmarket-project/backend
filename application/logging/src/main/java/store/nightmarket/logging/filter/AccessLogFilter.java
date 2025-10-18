package store.nightmarket.logging.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import store.nightmarket.logging.CustomLogger;
import store.nightmarket.logging.model.TypeEnum;
import store.nightmarket.logging.model.detail.AccessLog;

public class AccessLogFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {

		long start = System.currentTimeMillis();
		filterChain.doFilter(request, response);
		long duration = System.currentTimeMillis() - start;

		if (request instanceof ContentCachingRequestWrapper req
			&& response instanceof ContentCachingResponseWrapper res) {
			try {
				AccessLog logEvent = buildAccessLog(req, res, duration);
				CustomLogger.log(logEvent);
			} finally {
				res.copyBodyToResponse();
			}
		}

	}

	private AccessLog buildAccessLog(ContentCachingRequestWrapper req, ContentCachingResponseWrapper res,
		long elapsedTime) {
		return AccessLog.builder()
			.eventType(TypeEnum.ACCESS)
			.uri(req.getRequestURI())
			.method(req.getMethod())
			.status(res.getStatus())
			.responseTimeMs(elapsedTime)
			.requestHeaders(extractHeaders(req))
			.responseHeaders(extractHeaders(res))
			.requestBody(readRequestBody(req))
			.responseBody(readResponseBody(res))
			.build();
	}

	private Map<String, String> extractHeaders(HttpServletRequest request) {
		Map<String, String> headers = new LinkedHashMap<>();
		Enumeration<String> names = request.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			headers.put(name, request.getHeader(name));
		}
		return headers;
	}

	private Map<String, String> extractHeaders(HttpServletResponse response) {
		Map<String, String> headers = new LinkedHashMap<>();
		for (String name : response.getHeaderNames()) {
			headers.put(name, response.getHeader(name));
		}
		return headers;
	}

	private String readRequestBody(ContentCachingRequestWrapper request) {
		try {
			byte[] buf = request.getContentAsByteArray();

			if (buf.length == 0 && request.getContentLength() > 0) {
				request.getInputStream().readAllBytes();
				buf = request.getContentAsByteArray();
			}

			if (buf.length == 0)
				return null;

			return new String(buf, request.getCharacterEncoding());
		} catch (Exception e) {
			return null;
		}
	}

	private String readResponseBody(ContentCachingResponseWrapper response) {
		try {
			byte[] buf = response.getContentAsByteArray();

			if (buf.length == 0)
				return null;

			return new String(buf, response.getCharacterEncoding());
		} catch (Exception e) {
			return null;
		}
	}

}
