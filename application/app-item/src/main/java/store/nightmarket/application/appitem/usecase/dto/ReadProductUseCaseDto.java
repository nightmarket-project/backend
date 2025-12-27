package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;

public class ReadProductUseCaseDto {

	@Builder
	public record Input(
		ProductId productId
	) {

	}

	@Builder
	public record Output(
		Product product
	) {

	}

}
