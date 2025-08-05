package store.nightmarket.application.appuser.auth.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import store.nightmarket.application.appuser.auth.exception.OAuthException;
import store.nightmarket.application.appuser.auth.model.strategy.OAuthStrategy;

public class OAuthCallbackFilter extends AbstractAuthenticationProcessingFilter {

	private final Map<String, OAuthStrategy> oAuthStrategyMap;

	public OAuthCallbackFilter(List<OAuthStrategy> delegatorList) {
		super(new AntPathRequestMatcher("/login/oauth2/code/**", "GET"));
		this.oAuthStrategyMap = delegatorList.stream()
			.collect(Collectors.toMap(
				OAuthStrategy::getProviderName,
				delegator -> delegator
			));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		String provider = extractProviderFromUri(request.getRequestURI());

		OAuthStrategy oAuthStrategy = oAuthStrategyMap.get(provider);

		if (oAuthStrategy == null) {
			throw new OAuthException("Unsupported OAuth provider: " + provider);
		}

		return oAuthStrategy.delegate(request, getAuthenticationManager());
	}

	private String extractProviderFromUri(String uri) {
		String[] split = uri.split("/");
		return split[split.length - 1];
	}

}
