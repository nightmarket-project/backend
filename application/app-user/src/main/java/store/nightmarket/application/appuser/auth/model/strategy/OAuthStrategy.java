package store.nightmarket.application.appuser.auth.model.strategy;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface OAuthStrategy {

	Authentication delegate(HttpServletRequest request, AuthenticationManager authenticationManager);

	String getProviderName();

}
