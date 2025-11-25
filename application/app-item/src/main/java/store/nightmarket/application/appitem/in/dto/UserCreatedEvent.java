package store.nightmarket.application.appitem.in.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UserCreatedEvent(
	UUID userId,
	String name
) {

}
