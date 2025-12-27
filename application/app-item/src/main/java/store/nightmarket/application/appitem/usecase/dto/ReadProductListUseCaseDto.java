package store.nightmarket.application.appitem.usecase.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import store.nightmarket.domain.item.model.Product;

public class ReadProductListUseCaseDto {

	@Builder
	public record Input(
		int page,
		int size
	) {

	}

	@Builder
	public record Output(
		Page<Product> productPage
	) {

	}

}
