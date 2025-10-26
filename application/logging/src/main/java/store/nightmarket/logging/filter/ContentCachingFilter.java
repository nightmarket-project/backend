package store.nightmarket.logging.filter;

import java.io.IOException;

import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

		filterChain.doFilter(wrappedRequest, wrappedResponse);
		wrappedResponse.copyBodyToResponse();
	}

}
