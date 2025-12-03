package store.nightmarket.application.apporder.auth;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.constant.Constant;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) throws Exception {
		if (!(handler instanceof HandlerMethod handlerMethod)) {
			return true;
		}

		UserSession session = (UserSession)request.getAttribute(Constant.USER_SESSION);
		if (session == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		RequireRoles requireRoles = getRequireRoles(handlerMethod);
		if (requireRoles == null) {
			return true;
		}

		if (!isAllowed(requireRoles, session)) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}

		return true;
	}

	private static RequireRoles getRequireRoles(HandlerMethod handlerMethod) {
		RequireRoles requireRoles = handlerMethod.getMethodAnnotation(RequireRoles.class);
		if (requireRoles == null) {
			requireRoles = handlerMethod.getBeanType().getAnnotation(RequireRoles.class);
		}
		return requireRoles;
	}

	private static boolean isAllowed(RequireRoles requireRoles, UserSession session) {
		List<String> neededRoles = List.of(requireRoles.value());
		List<String> userRoles = session.roles();
		return userRoles.stream().anyMatch(neededRoles::contains);
	}

}