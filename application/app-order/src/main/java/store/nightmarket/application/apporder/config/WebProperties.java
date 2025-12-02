package store.nightmarket.application.apporder.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "web")
@Getter
@Setter
public class WebProperties {

	private Cors cors;
	private Interceptor interceptor;

	@Getter
	@Setter
	public static class Cors {
		private String pattern;
		private List<String> allowedOrigins;
		private String allowedMethods;
		private String allowedHeaders;
		private boolean allowCredentials;
		private long maxAge;
	}

	@Getter
	@Setter
	public static class Interceptor {
		private List<String> includePatterns;
	}

}
