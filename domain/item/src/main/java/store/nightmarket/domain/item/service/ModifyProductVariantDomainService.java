package store.nightmarket.domain.item.service;

import static store.nightmarket.domain.item.service.dto.ModifyProductVariantDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ProductVariant;

@Component
public class ModifyProductVariantDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		ProductVariant productVariant = input.productVariant();

		productVariant.edit(
			input.SKUCode(),
			input.quantity()
		);

		return Event.builder()
			.productVariant(productVariant)
			.build();
	}

}
