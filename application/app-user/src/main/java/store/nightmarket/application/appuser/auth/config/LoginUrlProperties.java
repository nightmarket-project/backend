package store.nightmarket.application.appuser.auth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "login")
public class LoginUrlProperties {

	private Map<String, String> urlMap = new HashMap<>();

}
