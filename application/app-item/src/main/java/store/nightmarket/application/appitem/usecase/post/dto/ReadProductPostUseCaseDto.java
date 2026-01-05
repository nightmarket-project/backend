package store.nightmarket.application.appitem.usecase.post.dto;

import lombok.Builder;
import store.nightmarket.application.appitem.out.post.mapper.dto.ProductPostAdapterDto;

public class ReadProductPostUseCaseDto {

	@Builder
	public record Output(
		ProductPostAdapterDto productPostAdapterDto
	) {

	}

}
