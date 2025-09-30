package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.model.ShoppingBasketProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

public class ShoppingBasketProductMapper {

	public static ShoppingBasketProduct toDomain(ShoppingBasketProductEntity entity) {
		return ShoppingBasketProduct.newInstanceWithCreatedAt(
			new ShoppingBasketProductId(entity.getId()),
			entity.getCreatedAt(),
			new ProductVariantId(entity.getProductVariantEntity().getId()),
			new UserId(entity.getUserEntity().getId()),
			new Name(entity.getNameEntity().getValue()),
			new Price(entity.getPriceEntity().getAmount()),
			new Quantity(entity.getQuantityEntity().getAmount())
		);
	}

	public static ShoppingBasketProductEntity toEntity(
		ShoppingBasketProduct domain,
		ProductVariantEntity productVariantEntity,
		UserEntity userEntity
	) {
		return ShoppingBasketProductEntity.newInstanceWithCreatedAt(
			domain.getShoppingBasketProductId().getId(),
			domain.getCreatedAt(),
			new NameEntity(domain.getName().getValue()),
			new QuantityEntity(domain.getQuantity().value()),
			new PriceEntity(domain.getUnitPrice().amount()),
			productVariantEntity,
			userEntity
		);
	}

}
