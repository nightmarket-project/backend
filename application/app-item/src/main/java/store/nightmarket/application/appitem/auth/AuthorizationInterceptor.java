package store.nightmarket.application.appitem.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static final String SESSION_KEY = "SPRING_SECURITY_CONTEXT";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response,
		Object handler) throws Exception {

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return false;
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			log.info("session");
			return false;
		}

		Object sessionAttr = session.getAttribute(SESSION_KEY);
		if (sessionAttr == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			log.info("securityContext");
			return false;
		}

		try {
			String jsonString = objectMapper.writeValueAsString(sessionAttr);

			JsonNode root = objectMapper.readTree(jsonString);
			JsonNode authNode = root.path("authentication");

			if (authNode.isMissingNode()) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				log.info("authNode");
				return false;
			}

			JsonNode principalNode = authNode.path("principal");
			String userId;

			if (principalNode.isTextual()) {
				userId = principalNode.asText();
			} else {
				userId = principalNode.path("id").asText(null);
			}

			String role = null;
			JsonNode authorities = authNode.path("authorities");

			if (authorities.isArray() && !authorities.isEmpty()) {
				for (JsonNode auth : authorities) {
					if (auth.has("authority")) {
						role = auth.path("authority").asText(null);
						break;
					}
				}
			}

			if (userId == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				log.info("Not Exists UserId");
				return false;
			}

			if (!"ROLE_BUYER".equals(role)) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				log.info("Role is Not Buyer");
				return false;
			}

			request.setAttribute("USER_SESSION", new UserSession(userId, role));
			return true;

		} catch (Exception e) {
			log.error("Error processing security context", e);
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"Internal server error\"}");
			return false;
		}
	}

}
