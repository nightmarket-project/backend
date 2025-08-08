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
import store.nightmarket.application.appuser.auth.model.strategy.OAuthGenerator;

public class OAuthCallbackFilter extends AbstractAuthenticationProcessingFilter {

	private final Map<String, OAuthGenerator> oAuthGeneratorMap;

	public OAuthCallbackFilter(List<OAuthGenerator> generatorList) {
		super(new AntPathRequestMatcher("/login/oauth2/code/**", "GET"));
		this.oAuthGeneratorMap = generatorList.stream()
			.collect(Collectors.toMap(
				OAuthGenerator::getProviderName,
				generator -> generator
			));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {

		String provider = extractProviderFromUri(request.getRequestURI());

		OAuthGenerator oAuthGenerator = oAuthGeneratorMap.get(provider.toUpperCase());

		if (oAuthGenerator == null) {
			throw new OAuthException("Unsupported OAuth provider: " + provider);
		}

		Authentication authentication = oAuthGenerator.generate(request);

		return this.getAuthenticationManager().authenticate(authentication);
	}

	private String extractProviderFromUri(String uri) {
		String[] split = uri.split("/");
		return split[split.length - 1];
	}

}
