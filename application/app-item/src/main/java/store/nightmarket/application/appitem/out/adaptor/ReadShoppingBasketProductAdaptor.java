package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.mapper.ShoppingBasketProductMapper;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.persistence.persistitem.repository.ShoppingBasketProductRepository;

@Component
@RequiredArgsConstructor
public class ReadShoppingBasketProductAdaptor implements ReadShoppingBasketProductPort {

	private final ShoppingBasketProductRepository shoppingBasketProductRepository;

	@Override
	public Optional<ShoppingBasketProduct> read(ShoppingBasketProductId shoppingBasketProductId) {
		return shoppingBasketProductRepository.findById(shoppingBasketProductId.getId())
			.map(ShoppingBasketProductMapper::toDomain);
	}

	@Override
	public List<ShoppingBasketProduct> readListByUserId(UserId userId) {
		return shoppingBasketProductRepository.findByUserId(userId.getId())
			.stream()
			.map(ShoppingBasketProductMapper::toDomain)
			.toList();
	}

}
