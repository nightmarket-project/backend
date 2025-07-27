package store.nightmarket.application.appuser.auth.model;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.valueobject.UserRole;

public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {
		User user = (User)authentication.getPrincipal();
		HttpSession session = request.getSession();

		if (user.getRole() == UserRole.ROLE_NONE) {
			response.sendRedirect("/"); //user 설정 페이지 추후에 추가
		} else {
			session.setAttribute("userId", user.getUserId().getId());
			session.setMaxInactiveInterval(3600);

			response.sendRedirect(""); //home 화면
		}
	}

}