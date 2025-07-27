package store.nightmarket.application.appuser.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.model.OAuthAuthenticationSuccessHandler;
import store.nightmarket.application.appuser.auth.model.OAuthCallbackFilter;
import store.nightmarket.application.appuser.auth.model.delegator.GoogleAuthenticationDelegator;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final GoogleAuthenticationDelegator googleDelegator;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		http
			.csrf(AbstractHttpConfigurer::disable)

			.cors(AbstractHttpConfigurer::disable)

			.httpBasic(AbstractHttpConfigurer::disable)

			.formLogin(AbstractHttpConfigurer::disable)

			.logout(AbstractHttpConfigurer::disable)

			.headers(c -> c.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::disable).disable())

			.addFilterBefore(callBackFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)

			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public OAuthCallbackFilter callBackFilter(AuthenticationManager authenticationManager) throws Exception {
		OAuthCallbackFilter oAuthCallbackFilter = new OAuthCallbackFilter(List.of(googleDelegator));
		oAuthCallbackFilter.setAuthenticationManager(authenticationManager);
		oAuthCallbackFilter.setAuthenticationSuccessHandler(successHandler());
		return oAuthCallbackFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public OAuthAuthenticationSuccessHandler successHandler() {
		return new OAuthAuthenticationSuccessHandler();
	}

}
