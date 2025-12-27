package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterProductUseCaseDto {

	@Builder
	public record Input(
		Name name,
		String description,
		Price price
	) {

	}

}
