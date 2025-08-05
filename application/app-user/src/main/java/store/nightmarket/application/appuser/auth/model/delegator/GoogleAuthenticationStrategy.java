package store.nightmarket.application.appuser.auth.model.delegator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import store.nightmarket.application.appuser.auth.exception.OAuthException;
import store.nightmarket.application.appuser.auth.model.GoogleOAuthAuthenticationToken;
import store.nightmarket.domain.user.valueobject.AuthProvider;

@Component
public class GoogleAuthenticationStrategy implements OAuthStrategy {

	@Override
	public Authentication delegate(HttpServletRequest request, AuthenticationManager authenticationManager) {

		String authorizationCode = request.getParameter("code");
		String state = request.getParameter("state");

		// 세션에 담긴 state로 검증
		HttpSession session = request.getSession();
		String sessionState = (String)session.getAttribute("session_state");

		if (sessionState == null || !sessionState.equals(state)) {
			throw new OAuthException("Invalid state parameter");
		}

		session.removeAttribute("session_state");

		GoogleOAuthAuthenticationToken token = new GoogleOAuthAuthenticationToken(authorizationCode);

		return authenticationManager.authenticate(token);

	}

	@Override
	public String getProviderName() {
		return AuthProvider.GOOGLE.name();
	}

}
