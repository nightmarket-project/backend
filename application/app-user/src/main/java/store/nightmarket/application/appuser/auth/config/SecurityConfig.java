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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.model.OAuthAuthenticationSuccessHandler;
import store.nightmarket.application.appuser.auth.model.OAuthCallbackFilter;
import store.nightmarket.application.appuser.auth.model.strategy.GoogleAuthenticationStrategy;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final GoogleAuthenticationStrategy googleAuthenticationStrategy;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		http
			.csrf(csrf -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringRequestMatchers("/api/v1/test/**")
			)
			.cors(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.headers(c -> c.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::disable).disable())
			.sessionManagement(session -> {
				session
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
					.sessionRegistry(sessionRegistry())
					.expiredUrl("/login?expired");

				session
					.sessionFixation().changeSessionId()
					.invalidSessionUrl("/login?invalid");
			})
			.logout(logout -> logout
				.logoutUrl("/api/v1/auth/logout")
				.logoutSuccessUrl("http://localhost:3000/?logout=success")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true)
			)
			.addFilterBefore(callBackFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth
				//OAuth2
				.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
				.requestMatchers("api/v1/order/**").hasRole("BUYER")
				.requestMatchers("/api/v1/oauth/authorization/**").permitAll()
				.requestMatchers("/login/oauth2/code/**").permitAll()
				.requestMatchers("/api/v1/test/login").permitAll()
				.requestMatchers("/api/v1/test/check").permitAll()
				.requestMatchers("/api/v1/test/session").hasRole("BUYER")
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public OAuthCallbackFilter callBackFilter(AuthenticationManager authenticationManager) throws Exception {
		OAuthCallbackFilter oAuthCallbackFilter = new OAuthCallbackFilter(List.of(googleAuthenticationStrategy));
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

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

}
