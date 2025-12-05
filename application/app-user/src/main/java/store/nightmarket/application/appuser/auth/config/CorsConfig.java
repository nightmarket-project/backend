package store.nightmarket.application.appuser.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

	private final WebProperties webProperties;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(webProperties.getCors().getAllowedOrigins()); // 모든 도메인 허용
		configuration.setAllowedMethods(webProperties.getCors().getAllowedMethods()); // 해당 method 허용
		configuration.setAllowedHeaders(webProperties.getCors().getAllowedHeaders()); // 모든 헤더 허용
		configuration.setAllowCredentials(webProperties.getCors().isAllowCredentials()); // 자격 증명 허용
		configuration.setMaxAge(webProperties.getCors().getMaxAge());

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(webProperties.getCors().getPattern(), configuration); // 모든 경로에 대해 적용
		return source;
	}

}
