package store.nightmarket.application.appuser.auth.model.delegator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface OAuthDelegator {

	Authentication delegate(HttpServletRequest request, AuthenticationManager authenticationManager);

	String getProviderName();

}
