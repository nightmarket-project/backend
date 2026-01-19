package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyProductDomainServiceDto {

	@Builder
	public record Input(
		Product product,
		Name name,
		String description,
		Price price
	) {

	}

	@Builder
	public record Event(
		Product product
	) {

	}

}
