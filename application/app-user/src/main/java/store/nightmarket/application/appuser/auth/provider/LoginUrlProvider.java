package store.nightmarket.application.appuser.auth.provider;

import java.util.UUID;

public interface LoginUrlProvider {

	String provide();

	String getProviderName();

	String getState();

	default String generateState() {
		return UUID.randomUUID().toString();
	}

}
