package store.nightmarket.application.appuser.out.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UserCreatedEvent(
	UUID userId,
	String name
) {

}
