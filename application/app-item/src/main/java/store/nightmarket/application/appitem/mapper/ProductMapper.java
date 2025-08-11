package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

public class ProductMapper {

	public static Product toDomain(ProductEntity entity) {
		return Product.newInstance(
			new ProductId(entity.getId()),
			new Name(entity.getNameEntity().getValue()),
			entity.getDescription(),
			new Price(entity.getPriceEntity().getAmount())
		);
	}

	public static ProductEntity toEntity(Product domain) {
		return ProductEntity.newInstance(
			domain.getProductId().getId(),
			new NameEntity(domain.getName().getValue()),
			domain.getDescription(),
			new PriceEntity(domain.getPrice().amount())
		);
	}

}
