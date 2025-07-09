package store.nightmarket.application.apporder.usecase.dto;

import java.util.UUID;

import lombok.Builder;

public class RequestOrderUseCaseDto {

	@Builder
	public record Input(
		AddressDto addressDto,
		UUID userId
	) {

	}

	@Builder
	public record AddressDto(
		String zipCode,
		String roadAddress,
		String detailAddress
	) {

	}

}
