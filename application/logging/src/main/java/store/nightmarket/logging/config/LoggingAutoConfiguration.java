package store.nightmarket.logging.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import jakarta.servlet.Filter;
import store.nightmarket.logging.filter.AccessLogFilter;
import store.nightmarket.logging.filter.ContentCachingFilter;

@AutoConfiguration
public class LoggingAutoConfiguration {

	@Bean
	public FilterRegistrationBean<Filter> contentCachingFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new ContentCachingFilter());
		registration.setOrder(Integer.MIN_VALUE); // 가장 먼저 실행
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public FilterRegistrationBean<Filter> loggingFilter() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new AccessLogFilter());
		registration.setOrder(Integer.MIN_VALUE + 1);
		registration.addUrlPatterns("/*");
		return registration;
	}

}
