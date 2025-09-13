package store.nightmarket.application.appitem.usecase.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;

public class FindProductByComponentUseCaseDto {

	@Builder
	public record Input(
		String component,
		int page
	) {

	}

	@Builder
	public record Output(
		Page<ProductPostAdapterDto> dtoPage
	) {

	}
}
