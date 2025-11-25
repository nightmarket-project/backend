package store.nightmarket.application.apporder.usecase.dto;

import java.util.UUID;

import lombok.Builder;

public class SaveProductVariantUseCaseDto {

	@Builder
	public record Input(
		UUID productVariantId,
		long price
	) {

	}

}
