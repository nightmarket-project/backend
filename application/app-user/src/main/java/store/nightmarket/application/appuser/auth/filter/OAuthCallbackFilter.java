package store.nightmarket.application.appuser.auth.filter;

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
import store.nightmarket.application.appuser.auth.provider.AuthenticationGenerator;

public class OAuthCallbackFilter extends AbstractAuthenticationProcessingFilter {

	private final Map<String, AuthenticationGenerator> generatorMap;

	public OAuthCallbackFilter(List<AuthenticationGenerator> generatorList) {
		super(new AntPathRequestMatcher("/login/oauth2/code/**", "GET"));
		this.generatorMap = generatorList.stream()
			.collect(Collectors.toMap(
				AuthenticationGenerator::getProviderName,
				generator -> generator
			));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		String provider = extractProviderFromUri(request.getRequestURI());

		AuthenticationGenerator authenticationGenerator = generatorMap.get(provider.toUpperCase());

		if (authenticationGenerator == null) {
			throw new OAuthException("Unsupported OAuth provider: " + provider);
		}

		Authentication authentication = authenticationGenerator.generate(request);

		return this.getAuthenticationManager().authenticate(authentication);
	}

	private String extractProviderFromUri(String uri) {
		String[] split = uri.split("/");
		return split[split.length - 1];
	}

}
