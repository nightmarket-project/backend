package store.nightmarket.application.appuser.auth.provider;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationGenerator {

	Authentication generate(HttpServletRequest request);

	String getProviderName();

}
