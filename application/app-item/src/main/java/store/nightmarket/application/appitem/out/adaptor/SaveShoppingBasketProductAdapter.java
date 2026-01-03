package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.mapper.ShoppingBasketProductMapper;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.persistence.persistitem.repository.ShoppingBasketProductRepository;

@Component
@RequiredArgsConstructor
public class SaveShoppingBasketProductAdapter implements SaveShoppingBasketProductPort {

	private final ShoppingBasketProductRepository shoppingBasketProductRepository;

	@Override
	public void save(ShoppingBasketProduct shoppingBasketProduct) {
		shoppingBasketProductRepository.save(ShoppingBasketProductMapper.toEntity(shoppingBasketProduct));
	}

}
