package store.nightmarket.application.appuser.auth.config;

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

	@Getter
	@Setter
	public static class Cors {
		private String pattern;
		private List<String> allowedOrigins;
		private List<String> allowedMethods;
		private List<String> allowedHeaders;
		private boolean allowCredentials;
		private long maxAge;
	}

}