package store.nightmarket.application.appuser.auth.model.strategy;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import store.nightmarket.application.appuser.auth.exception.OAuthException;
import store.nightmarket.application.appuser.auth.model.GoogleOAuthAuthenticationToken;
import store.nightmarket.domain.user.valueobject.AuthProvider;

@Component
public class GoogleAuthenticationGenerator implements OAuthGenerator {

	@Override
	public Authentication generate(HttpServletRequest request) {

		String authorizationCode = request.getParameter("code");
		String state = request.getParameter("state");

		HttpSession session = request.getSession();
		String sessionState = (String)session.getAttribute("session_state");

		if (sessionState == null || !sessionState.equals(state)) {
			throw new OAuthException("Invalid state parameter");
		}

		session.removeAttribute("session_state");

		return new GoogleOAuthAuthenticationToken(authorizationCode);
	}

	@Override
	public String getProviderName() {
		return AuthProvider.GOOGLE.name();
	}

}
