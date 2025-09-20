package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ModifyShoppingBasketQuantityUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.itemweb.service.PutProductIntoShoppingBasketItemWebDomainService;
import store.nightmarket.itemweb.service.dto.PutProductIntoShoppingBasketItemWebDomainServiceDto;

@Service
@RequiredArgsConstructor
public class ModifyShoppingBasketQuantityUseCase implements BaseUseCase<Input, Void> {

	private final ReadShoppingBasketProductPort readShoppingBasketProductPort;
	private final SaveShoppingBasketProductPort saveShoppingBasketProductPort;
	private final PutProductIntoShoppingBasketItemWebDomainService putProductIntoShoppingBasketItemWebDomainService;

	@Override
	public Void execute(Input input) {
		ShoppingBasketProduct shoppingBasketProduct = readShoppingBasketProductPort.readOrThrow(
			input.shoppingBasketProductId());

		PutProductIntoShoppingBasketItemWebDomainServiceDto.Input domainInput = PutProductIntoShoppingBasketItemWebDomainServiceDto.Input.builder()
			.shoppingBasketProduct(shoppingBasketProduct)
			.userId(input.userId())
			.quantity(input.quantity())
			.build();

		PutProductIntoShoppingBasketItemWebDomainServiceDto.Event event = putProductIntoShoppingBasketItemWebDomainService
			.execute(domainInput);

		saveShoppingBasketProductPort.save(event.getShoppingBasketProduct());
		return null;
	}

}
