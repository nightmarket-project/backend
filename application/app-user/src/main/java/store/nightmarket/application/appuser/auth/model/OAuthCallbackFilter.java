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
import store.nightmarket.application.appuser.auth.model.delegator.OAuthDelegator;

public class OAuthCallbackFilter extends AbstractAuthenticationProcessingFilter {

	private final Map<String, OAuthDelegator> delegators;

	public OAuthCallbackFilter(List<OAuthDelegator> delegatorList) {
		super(new AntPathRequestMatcher("/login/oauth2/code/**", "GET"));
		this.delegators = delegatorList.stream()
			.collect(Collectors.toMap(
				OAuthDelegator::getProviderName,
				delegator -> delegator
			));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		String provider = extractProviderFromUri(request.getRequestURI());

		OAuthDelegator oAuthDelegator = delegators.get(provider);

		if (oAuthDelegator == null) {
			throw new OAuthException("Unsupported OAuth provider: " + provider);
		}

		return oAuthDelegator.delegate(request, getAuthenticationManager());
	}

	private String extractProviderFromUri(String uri) {
		String[] split = uri.split("/");
		return split[split.length - 1];
	}

}
