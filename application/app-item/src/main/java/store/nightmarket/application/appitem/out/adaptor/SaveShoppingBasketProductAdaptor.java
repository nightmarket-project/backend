package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ShoppingBasketProductMapper;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.persistence.persistitem.entity.repository.ProductVariantRepository;
import store.nightmarket.persistence.persistitem.entity.repository.ShoppingBasketProductRepository;
import store.nightmarket.persistence.persistitem.entity.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SaveShoppingBasketProductAdaptor implements SaveShoppingBasketProductPort {

	private final ShoppingBasketProductRepository shoppingBasketProductRepository;
	private final UserRepository userRepository;
	private final ProductVariantRepository productVariantRepository;

	@Override
	public void save(ShoppingBasketProduct shoppingBasketProduct) {
		shoppingBasketProductRepository.save(
			ShoppingBasketProductMapper.toEntity(
				shoppingBasketProduct,
				productVariantRepository.getReferenceById(shoppingBasketProduct.getVariantId().getId()),
				userRepository.getReferenceById(shoppingBasketProduct.getUserId().getId())
			)
		);
	}

}
