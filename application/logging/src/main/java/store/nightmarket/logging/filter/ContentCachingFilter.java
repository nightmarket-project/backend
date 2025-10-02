package store.nightmarket.logging.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContentCachingFilter implements Filter {

	@Override
	public void doFilter(
		ServletRequest servletRequest,
		ServletResponse servletResponse,
		FilterChain filterChain
	) throws IOException, ServletException {

		ContentCachingRequestWrapper wrappedRequest =
			new ContentCachingRequestWrapper((HttpServletRequest)servletRequest);

		ContentCachingResponseWrapper wrappedResponse =
			new ContentCachingResponseWrapper((HttpServletResponse)servletResponse);

		try {
			filterChain.doFilter(wrappedRequest, wrappedResponse);
		} finally {
			wrappedResponse.copyBodyToResponse();
		}
	}

}
