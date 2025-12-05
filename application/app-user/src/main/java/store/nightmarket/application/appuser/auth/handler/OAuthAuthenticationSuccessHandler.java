package store.nightmarket.application.appuser.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.auth.config.LoginUrlProperties;
import store.nightmarket.application.appuser.auth.constant.Constant;

@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final LoginUrlProperties loginUrlProperties;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException {
		response.sendRedirect(loginUrlProperties.getUrlMap().get(Constant.LOGIN_SUCCESS));
	}

}