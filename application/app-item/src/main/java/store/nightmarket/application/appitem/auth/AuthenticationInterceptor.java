package store.nightmarket.application.appitem.auth;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.constant.Constant;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			response.setStatus(HttpStatus.OK.value());
			return false;
		}

		SecurityContext.Authentication authentication = getAuthentication(request);
		if (isNotValidAuthentication(authentication)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		String userId = authentication.principal().userId();
		List<String> roles = hasRole(authentication) ?
			authentication.authorities().stream().map(SecurityContext.Authentication.Authority::authority).toList() :
			null;
		request.setAttribute(Constant.USER_SESSION, new UserSession(userId, roles));
		return true;
	}

	private boolean isNotValidAuthentication(SecurityContext.Authentication authentication) {
		return (authentication == null) || (authentication.principal().userId() == null);
	}

	private static boolean hasRole(SecurityContext.Authentication authentication) {
		return (authentication.authorities() != null)
			&& (!authentication.authorities().isEmpty());
	}

	private SecurityContext.Authentication getAuthentication(
		HttpServletRequest request
	) {
		SecurityContext securityContext = getSecurityContext(request);
		if (securityContext == null) {
			return null;
		}

		SecurityContext.Authentication auth = securityContext.authentication();
		if (auth == null || auth.principal() == null) {
			return null;
		}

		return auth;
	}

	private SecurityContext getSecurityContext(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}

		Object sessionAttr = session.getAttribute(Constant.SESSION_KEY);
		if (sessionAttr == null) {
			return null;
		}

		try {
			String jsonString = objectMapper.writeValueAsString(sessionAttr);
			return objectMapper.readValue(jsonString, SecurityContext.class);
		} catch (Exception e) {
			log.warn("Cannot Deserialize SecurityContext");
			return null;
		}
	}

}
