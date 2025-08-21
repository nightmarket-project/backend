package store.nightmarket.application.apporder.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.domain.order.valueobject.ProductVariantId;
import store.nightmarket.domain.order.valueobject.Quantity;

public class SaveOrderDto {

	@Builder
	public record Request(
		AddressDto addressDto,
		UUID userId,
		List<DetailOrderDto> detailOrderDtoList
	) {

	}

	@Builder
	public record AddressDto(
		String zipCode,
		String roadAddress,
		String detailAddress
	) {

	}

	@Builder
	public record DetailOrderDto(
		ProductVariantId productVariantId,
		Quantity quantity
	) {

	}

}
