package store.nightmarket.logging.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import store.nightmarket.logging.CummicationLogger;
import store.nightmarket.logging.CommunicationLog;
import store.nightmarket.logging.model.AccessLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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

			AccessLog logEvent = buildAccessLog(req, res, duration);
			CummicationLogger.log(logEvent);
		}
	}

	private AccessLog buildAccessLog(ContentCachingRequestWrapper req, ContentCachingResponseWrapper res,
		long elapsedTime) {
		return AccessLog.builder()
			.eventType(CommunicationLog.Type.ACCESS)
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
				buf = StreamUtils.copyToByteArray(request.getInputStream());
			}

			String encoding = Optional.of(request.getCharacterEncoding())
                    .orElse(StandardCharsets.UTF_8.name());

			return new String(buf, encoding);
		} catch (Exception e) {
			return null;
		}
	}

	private String readResponseBody(ContentCachingResponseWrapper response) {
		try {
			byte[] buf = response.getContentAsByteArray();

			String encoding = Optional.of(response.getCharacterEncoding())
                    .orElse(StandardCharsets.UTF_8.name());

			return new String(buf, encoding);
		} catch (Exception e) {
			return null;
		}
	}

}
