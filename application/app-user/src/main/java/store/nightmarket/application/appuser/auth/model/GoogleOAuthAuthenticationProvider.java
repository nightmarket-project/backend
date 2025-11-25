package store.nightmarket.application.appuser.auth.model;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.dto.GoogleAccessTokenDto;
import store.nightmarket.application.appuser.auth.dto.GoogleUserDto;
import store.nightmarket.application.appuser.auth.dto.OAuthProviderProperties;
import store.nightmarket.application.appuser.auth.dto.UserAuthentication;
import store.nightmarket.application.appuser.auth.model.feign.GoogleAuthApi;
import store.nightmarket.application.appuser.auth.model.feign.GoogleUserApi;
import store.nightmarket.application.appuser.user.mapper.UserMapper;
import store.nightmarket.application.appuser.user.out.ReadUserPort;
import store.nightmarket.application.appuser.user.out.SaveUserPort;
import store.nightmarket.application.appuser.user.out.adapter.UserCreatedEventKafkaPublisher;
import store.nightmarket.application.appuser.user.out.dto.UserCreatedEvent;
import store.nightmarket.domain.user.model.User;

@Component
@RequiredArgsConstructor
public class GoogleOAuthAuthenticationProvider implements AuthenticationProvider {

	private final OAuthProviderProperties properties;
	private final ReadUserPort readUserPort;
	private final SaveUserPort saveUserPort;
	private final GoogleAuthApi googleAuthApi;
	private final GoogleUserApi googleUserApi;
	private final UserCreatedEventKafkaPublisher userCreatedEventKafkaPublisher;
	private static final String PROVIDER_NAME = "google";
	private static final String GRANT_TYPE = "authorization_code";
	private static final String AUTHORIZATION_HEADER = "Bearer ";

	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		GoogleOAuthAuthenticationToken token = (GoogleOAuthAuthenticationToken)authentication;

		String accessCode = token.getAccessCode();

		GoogleAccessTokenDto.Response accessToken = getAccessToken(accessCode);

		GoogleUserDto googleUser = getGoogleUser(accessToken.getAccessToken());

		User user = readUserPort.readByEmail(googleUser.getEmail())
			.orElseGet(() -> saveUserAndPublishUserCreatedEvent(googleUser));

		return new UserAuthentication(
			user.getUserId().getId().toString(),
			user.getName().getValue(),
			Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
		);
	}

	private GoogleAccessTokenDto.Response getAccessToken(String accessCode) {
		return googleAuthApi.getAccessToken(
			GoogleAccessTokenDto.Request.builder()
				.code(accessCode)
				.client_id(properties.getProviders().get(PROVIDER_NAME).getClientId())
				.clientSecret(properties.getProviders().get(PROVIDER_NAME).getClientSecret())
				.redirect_uri(properties.getProviders().get(PROVIDER_NAME).getRedirectUri())
				.grant_type(GRANT_TYPE)
				.build()
		);
	}

	private GoogleUserDto getGoogleUser(String accessToken) {
		return googleUserApi.getUserInfo(AUTHORIZATION_HEADER + accessToken);
	}

	private User saveUserAndPublishUserCreatedEvent(GoogleUserDto googleUser) {
		User user = saveUserPort.save(UserMapper.toDomainFromGoogleUser(googleUser));

		userCreatedEventKafkaPublisher.publishEvent(
			UserCreatedEvent.builder()
				.userId(user.getUserId().getId())
				.name(user.getName().getValue())
				.build()
		);

		return user;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return GoogleOAuthAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
