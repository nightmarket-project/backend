package store.nightmarket.application.appuser.user.out.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UserCreatedEvent(
	UUID userId,
	String name
) {

}
