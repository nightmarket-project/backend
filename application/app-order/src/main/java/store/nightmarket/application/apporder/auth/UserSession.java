package store.nightmarket.application.apporder.auth;

import lombok.Builder;

@Builder
public record UserSession(
	String userId,
	String role
) {

}