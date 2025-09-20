package store.nightmarket.application.appitem.usecase.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;

public class ReadProductVariantUseCaseDto {

	@Builder
	public record Output(
		List<ProductVariantAdapterDto> productVariantAdapterDtoList
	) {

	}

}
