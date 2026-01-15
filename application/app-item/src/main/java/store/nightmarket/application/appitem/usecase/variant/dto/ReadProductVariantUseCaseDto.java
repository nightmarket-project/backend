package store.nightmarket.application.appitem.usecase.variant.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.mapper.dto.ProductVariantAdapterDto;

public class ReadProductVariantUseCaseDto {

	@Builder
	public record Output(
		List<ProductVariantAdapterDto> productVariantAdapterDtoList
	) {

	}

}
