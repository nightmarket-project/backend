package store.nightmarket.application.apporder.auth;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static final String SESSION_KEY = "SPRING_SECURITY_CONTEXT";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response,
		Object handler) throws Exception {

		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			response.setStatus(HttpStatus.OK.value());
			return false;
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		Object sessionAttr = session.getAttribute(SESSION_KEY);
		if (sessionAttr == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}

		try {
			String jsonString = objectMapper.writeValueAsString(sessionAttr);
			SecurityContext securityContext = objectMapper.readValue(jsonString, SecurityContext.class);

			SecurityContext.Authentication auth = securityContext.authentication();

			if (auth == null || auth.principal() == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

			String userId = auth.principal().userId();
			String role = (auth.authorities() != null && !auth.authorities().isEmpty())
				? auth.authorities().getFirst().authority()
				: null;

			if (userId == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				return false;
			}

			if (!"ROLE_BUYER".equals(role)) {
				response.setStatus(HttpStatus.FORBIDDEN.value());
				return false;
			}

			request.setAttribute("USER_SESSION", new UserSession(userId, role));
			return true;

		} catch (Exception e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"Invalid session data\"}");
			return false;
		}
	}

}