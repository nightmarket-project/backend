package store.nightmarket.application.appitem.usecase.cart;

import static store.nightmarket.application.appitem.usecase.cart.dto.ModifyShoppingBasketQuantityUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.service.PutProductIntoShoppingBasketDomainService;
import store.nightmarket.domain.item.service.dto.PutProductIntoShoppingBasketDomainServiceDto;

@Service
@RequiredArgsConstructor
public class ModifyShoppingBasketQuantityUseCase implements BaseUseCase<Input, Void> {

	private final ReadShoppingBasketProductPort readShoppingBasketProductPort;
	private final SaveShoppingBasketProductPort saveShoppingBasketProductPort;
	private final PutProductIntoShoppingBasketDomainService putProductIntoShoppingBasketDomainService;

	@Override
	public Void execute(Input input) {
		ShoppingBasketProduct shoppingBasketProduct = readShoppingBasketProductPort.readOrThrow(
			input.shoppingBasketProductId());

		PutProductIntoShoppingBasketDomainServiceDto.Input domainInput = PutProductIntoShoppingBasketDomainServiceDto.Input.builder()
			.shoppingBasketProduct(shoppingBasketProduct)
			.userId(input.userId())
			.quantity(input.quantity())
			.build();

		PutProductIntoShoppingBasketDomainServiceDto.Event event = putProductIntoShoppingBasketDomainService
			.execute(domainInput);

		saveShoppingBasketProductPort.save(event.getShoppingBasketProduct());
		return null;
	}

}
