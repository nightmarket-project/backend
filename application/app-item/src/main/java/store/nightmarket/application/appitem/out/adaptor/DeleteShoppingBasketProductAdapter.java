package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteShoppingBasketProductPort;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.persistence.persistitem.repository.ShoppingBasketProductRepository;

@Component
@RequiredArgsConstructor
public class DeleteShoppingBasketProductAdapter implements DeleteShoppingBasketProductPort {

	private final ShoppingBasketProductRepository shoppingBasketProductRepository;

	@Override
	public void delete(ShoppingBasketProductId shoppingBasketProductId) {
		shoppingBasketProductRepository.deleteById(shoppingBasketProductId.getId());
	}

}
