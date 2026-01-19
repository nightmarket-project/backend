package store.nightmarket.domain.item.service;

import static store.nightmarket.domain.item.service.dto.ModifyProductDomainServiceDto.*;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.Product;

@Component
public class ModifyProductDomainService implements BaseDomainService<Input, Event> {

	@Override
	public Event execute(Input input) {
		Product product = input.product();

		product.edit(
			input.name(),
			input.description(),
			input.price()
		);

		return Event.builder()
			.product(product)
			.build();
	}

}
