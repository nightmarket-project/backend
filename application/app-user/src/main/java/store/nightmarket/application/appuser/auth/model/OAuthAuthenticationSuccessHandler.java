package store.nightmarket.application.appuser.auth.model;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.domain.user.valueobject.UserId;

@Slf4j
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {
		UserId userId = (UserId)authentication.getPrincipal();

		HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(3600);

		log.info("Session created for userId: {} with sessionId: {}", userId, session.getId());

		response.sendRedirect("http://localhost:3000/?login=success");
	}

}