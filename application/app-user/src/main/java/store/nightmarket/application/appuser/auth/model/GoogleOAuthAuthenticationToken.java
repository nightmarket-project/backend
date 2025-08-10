package store.nightmarket.application.appuser.auth.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class GoogleOAuthAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private final String accessCode;

	public GoogleOAuthAuthenticationToken(String authorizationCode) {
		super(null);
		this.principal = null;
		this.accessCode = authorizationCode;
		this.setAuthenticated(false);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	public String getAccessCode() {
		return accessCode;
	}

}
