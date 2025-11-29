package store.nightmarket.application.appitem.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.AuthorizationInterceptor;
import store.nightmarket.application.appitem.auth.AuthorizedUserArgumentResolver;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthorizationInterceptor authorizationInterceptor;
	private final AuthorizedUserArgumentResolver authorizedUserArgumentResolver;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOriginPatterns("http://localhost:3000", "https://localhost:3000",
				"https://item.syua-test.duckdns.org")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
			.allowedHeaders("*")
			.allowCredentials(true)
			.maxAge(3600);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor)
			.addPathPatterns("/api/v1/cart/**");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authorizedUserArgumentResolver);
	}

}