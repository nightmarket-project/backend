package store.nightmarket.application.apporder.in.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ProductCreatedEvent(
	UUID id,
	long price
) {

}
