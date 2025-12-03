package store.nightmarket.application.apporder.auth;

import java.util.List;

import lombok.Builder;

@Builder
public record UserSession(
	String userId,
	List<String> roles
) {

}