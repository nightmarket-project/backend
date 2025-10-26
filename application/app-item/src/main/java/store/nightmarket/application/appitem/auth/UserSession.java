package store.nightmarket.application.appitem.auth;

import lombok.Builder;

@Builder
public record UserSession(
	String userId,
	String role
) {

}