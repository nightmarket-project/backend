package store.nightmarket.application.apporder.usecase.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import store.nightmarket.application.apporder.in.dto.SaveOrderDto;
import store.nightmarket.domain.order.valueobject.ProductVariantId;
import store.nightmarket.domain.order.valueobject.Quantity;

public class RequestOrderUseCaseDto {

	@Builder
	public record Input(
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

	public static List<DetailOrderDto> toUseCaseDto(List<SaveOrderDto.DetailOrderDto> detailOrderDtoList) {
		return detailOrderDtoList.stream()
			.map(dto -> new DetailOrderDto.DetailOrderDtoBuilder()
				.productVariantId(new ProductVariantId(dto.productVariantId()))
				.quantity(new Quantity(dto.quantity()))
				.build()
			).toList();
	}

}
