package store.nightmarket.application.appitem.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.AuthenticationInterceptor;
import store.nightmarket.application.appitem.auth.AuthorizationInterceptor;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUserArgumentResolver;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;
	private final AuthorizationInterceptor authorizationInterceptor;
	private final AuthorizedUserArgumentResolver authorizedUserArgumentResolver;
	private final WebProperties webProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping(webProperties.getCors().getPattern())
			.allowedOriginPatterns(webProperties.getCors().getAllowedOrigins().toArray(new String[0]))
			.allowedMethods(webProperties.getCors().getAllowedMethods().toArray(new String[0]))
			.allowedHeaders(webProperties.getCors().getAllowedHeaders().toArray(new String[0]))
			.allowCredentials(webProperties.getCors().isAllowCredentials())
			.maxAge(webProperties.getCors().getMaxAge());

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor).order(1)
			.addPathPatterns(webProperties.getInterceptor().getIncludePatterns());
		registry.addInterceptor(authorizationInterceptor).order(2)
			.addPathPatterns(webProperties.getInterceptor().getIncludePatterns());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authorizedUserArgumentResolver);
	}

}