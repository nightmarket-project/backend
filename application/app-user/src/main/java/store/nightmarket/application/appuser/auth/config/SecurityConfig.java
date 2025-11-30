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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.handler.OAuthAuthenticationSuccessHandler;
import store.nightmarket.application.appuser.auth.filter.OAuthCallbackFilter;
import store.nightmarket.application.appuser.auth.provider.google.GoogleAuthenticationGenerator;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final GoogleAuthenticationGenerator googleAuthenticationGenerator;
	private final CorsConfig corsConfig;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		http
			.requestCache(cache -> cache.requestCache(new NullRequestCache()))
			.csrf(csrf -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.ignoringRequestMatchers("/api/v1/test/*")
				.ignoringRequestMatchers("/api/v1/auth/*")
				.ignoringRequestMatchers("/h2-console/*")
			)
			.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.headers(c -> c.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::disable).disable()
			)
			.sessionManagement(session -> {
				session
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
					.sessionRegistry(sessionRegistry())
					.expiredUrl("/login/expired");

				session
					.sessionFixation().changeSessionId()
					.invalidSessionUrl("/login/invalid");
			})
			.securityContext(context -> context
				.requireExplicitSave(false) //true는 수동
				.securityContextRepository(securityContextRepository())
			)
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/logout", "POST"))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true)
				.logoutSuccessHandler((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType("application/json");
					response.getWriter().write("{\"message\": \"logout success\"}");
				})
			)
			.addFilterBefore(callBackFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth
				//OAuth2
				.requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
				.requestMatchers("api/v1/order/**").hasRole("BUYER")
				.requestMatchers("/api/v1/oauth/**").permitAll()
				.requestMatchers("/api/v1/test/**").permitAll()
				.requestMatchers("/login/oauth2/code/**").permitAll()
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public OAuthCallbackFilter callBackFilter(AuthenticationManager authenticationManager) {
		OAuthCallbackFilter oAuthCallbackFilter = new OAuthCallbackFilter(List.of(googleAuthenticationGenerator));
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

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

}
