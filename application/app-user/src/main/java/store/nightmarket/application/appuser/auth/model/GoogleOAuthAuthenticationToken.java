package store.nightmarket.application.appuser.auth.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class GoogleOAuthAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;
	private final String accessCode;
	private final String state;

	public GoogleOAuthAuthenticationToken(String authorizationCode, String state) {
		super(null);
		this.principal = null;
		this.accessCode = authorizationCode;
		this.state = state;
		this.setAuthenticated(false);
	}

	public GoogleOAuthAuthenticationToken(Object principal) {
		super(null);
		this.principal = principal;
		this.accessCode = null;
		this.state = null;
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
