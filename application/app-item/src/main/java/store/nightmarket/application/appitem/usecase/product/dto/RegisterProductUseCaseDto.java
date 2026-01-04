package store.nightmarket.application.appitem.usecase.product.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterProductUseCaseDto {

	@Builder
	public record Input(
		UserId userId,
		Name name,
		String description,
		Price price
	) {

	}

}
