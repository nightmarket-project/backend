package store.nightmarket.application.appuser.auth.provider.google;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.config.OAuthProviderProperties;
import store.nightmarket.application.appuser.auth.provider.LoginUrlProvider;
import store.nightmarket.domain.user.model.AuthProvider;

@Component
@RequiredArgsConstructor
public class GoogleLoginUrlProvider implements LoginUrlProvider {

	private final OAuthProviderProperties properties;
	private final String state = generateState();

	@Override
	public String provide() {
		return properties.getProviders().get(getProviderName().toLowerCase()).getSocialLoginUri() +
			"?client_id=" + properties.getProviders().get(getProviderName().toLowerCase()).getClientId() +
			"&redirect_uri=" + properties.getProviders().get(getProviderName().toLowerCase()).getRedirectUri() +
			"&response_type=code" +
			"&scope=email profile" +
			"&state=" + getState();
	}

	@Override
	public String getProviderName() {
		return AuthProvider.GOOGLE.name();
	}

	@Override
	public String getState() {
		return state;
	}

}
