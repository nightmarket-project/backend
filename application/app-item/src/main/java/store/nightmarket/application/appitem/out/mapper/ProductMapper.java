package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

public class ProductMapper {

	public static Product toDomain(ProductEntity entity) {
		return Product.newInstanceWithCreatedAt(
			new ProductId(entity.getId()),
			entity.getCreatedAt(),
			new UserId(entity.getUserId()),
			new Name(entity.getNameEntity().getValue()),
			entity.getDescription(),
			new Price(entity.getPriceEntity().getAmount())
		);
	}

	public static ProductEntity toEntity(Product domain) {
		return ProductEntity.newInstanceWithCreatedAt(
			domain.getProductId().getId(),
			domain.getCreatedAt(),
			domain.getUserId().getId(),
			new NameEntity(domain.getName().getValue()),
			domain.getDescription(),
			new PriceEntity(domain.getPrice().amount())
		);
	}

}
