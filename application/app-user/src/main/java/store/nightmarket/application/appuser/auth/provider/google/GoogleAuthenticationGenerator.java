package store.nightmarket.application.appuser.auth.provider.google;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import store.nightmarket.application.appuser.auth.constant.Constant;
import store.nightmarket.application.appuser.auth.exception.OAuthException;
import store.nightmarket.application.appuser.auth.provider.AuthenticationGenerator;
import store.nightmarket.application.appuser.auth.provider.google.model.GoogleOAuthAuthenticationToken;
import store.nightmarket.domain.user.model.AuthProvider;

@Component
public class GoogleAuthenticationGenerator implements AuthenticationGenerator {

	@Override
	public Authentication generate(HttpServletRequest request) {

		String authorizationCode = request.getParameter("code");
		String state = request.getParameter("state");

		HttpSession session = request.getSession();
		String sessionState = (String)session.getAttribute(Constant.SESSION_STATE);

		if (sessionState == null || !sessionState.equals(state)) {
			throw new OAuthException("Invalid state parameter");
		}

		session.removeAttribute(Constant.SESSION_STATE);

		return new GoogleOAuthAuthenticationToken(authorizationCode);
	}

	@Override
	public String getProviderName() {
		return AuthProvider.GOOGLE.name();
	}

}
