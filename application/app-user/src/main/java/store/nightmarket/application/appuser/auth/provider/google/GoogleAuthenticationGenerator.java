package store.nightmarket.application.appuser.auth.provider.google;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.constant.Constant;
import store.nightmarket.application.appuser.auth.exception.OAuthException;
import store.nightmarket.application.appuser.auth.provider.AuthenticationGenerator;
import store.nightmarket.application.appuser.auth.provider.google.model.GoogleOAuthAuthenticationToken;
import store.nightmarket.domain.user.model.AuthProvider;

@Component
@RequiredArgsConstructor
public class GoogleAuthenticationGenerator implements AuthenticationGenerator {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public Authentication generate(HttpServletRequest request) {

		String authorizationCode = request.getParameter("code");
		String state = request.getParameter("state");

		String sessionState = redisTemplate.opsForValue().get(Constant.SESSION_STATE);

		if (sessionState == null || !sessionState.equals(state)) {
			throw new OAuthException("Invalid state parameter");
		}

		redisTemplate.delete(Constant.SESSION_STATE);

		return new GoogleOAuthAuthenticationToken(authorizationCode);
	}

	@Override
	public String getProviderName() {
		return AuthProvider.GOOGLE.name();
	}

}
