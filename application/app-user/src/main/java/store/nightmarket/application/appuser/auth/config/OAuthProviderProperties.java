package store.nightmarket.application.appuser.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Configuration
@ConfigurationProperties(prefix = "oauth")
public class OAuthProviderProperties {

	private Map<String, OAuthProvider> providers = new HashMap<>();

	@Getter
	@Setter
	public static class OAuthProvider {
		private String clientId;
		private String clientSecret;
		private String redirectUri;
		private String socialLoginUri;
	}

}
