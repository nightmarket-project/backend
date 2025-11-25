package store.nightmarket.application.appitem.usecase.dto;

import java.util.UUID;

import lombok.Builder;

public class SaveUserUseCaseDto {

	@Builder
	public record Input(
		UUID userId,
		String name
	) {

	}

}
