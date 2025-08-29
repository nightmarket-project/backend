package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.ProductPostDto;

public class ReadProductPostUseCaseDto {

	@Builder
	public record Output(
		ProductPostDto productPostDto
	) {

	}

}
