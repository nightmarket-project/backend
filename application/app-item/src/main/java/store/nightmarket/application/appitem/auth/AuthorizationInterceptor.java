package store.nightmarket.application.appitem.auth;

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
        };

        // TODO : UserSession이 여러개의 role을 가질 수 있도록 구현 변경 필요
        String userId = authentication.principal().userId();
        String role = hasRole(authentication) ? authentication.authorities().getFirst().authority() : null;
        request.setAttribute("USER_SESSION", new UserSession(userId, role));
        return true;
    }

    private boolean isNotValidAuthentication(SecurityContext.Authentication authentication) {
        return (authentication != null) && (authentication.principal().userId() != null);
    }

    private static boolean hasRole(SecurityContext.Authentication authentication) {
        return (authentication.authorities() == null)
                || (authentication.authorities().isEmpty());
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

        Object sessionAttr = session.getAttribute(SESSION_KEY);
        if (sessionAttr == null) {
            return null;
        }

        try {
            String jsonString = objectMapper.writeValueAsString(sessionAttr);
            return objectMapper.readValue(jsonString, SecurityContext.class);
        } catch (Exception e) {
            // TODO : log 추가
            return null;
        }
    }

}
