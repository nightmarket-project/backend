package store.nightmarket.application.apporder.in.dto;

import java.util.UUID;

import lombok.Builder;

public class SaveOrderDto {

	@Builder
	public record Request(
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
