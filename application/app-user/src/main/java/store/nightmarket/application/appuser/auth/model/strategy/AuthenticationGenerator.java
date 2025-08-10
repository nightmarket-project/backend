package store.nightmarket.application.appuser.auth.model.strategy;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationGenerator {

	Authentication generate(HttpServletRequest request);

	String getProviderName();

}
