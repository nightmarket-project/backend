package store.nightmarket.application.apppayment.mapper;

import store.nightmarket.domain.payment.model.Product;
import store.nightmarket.domain.payment.model.id.ProductId;
import store.nightmarket.persistence.persistpayment.entity.model.ProductEntity;

public class ProductMapper {

	public static Product toDomain(ProductEntity entity) {
		return Product.newInstanceWithCreatedAt(
			new ProductId(entity.getId()),
			entity.getCreatedAt(),
			PriceMapper.toDomain(entity.getPriceEntity())
		);
	}

	public static ProductEntity toEntity(Product domain) {
		return new ProductEntity(
			domain.getProductId().getId(),
			domain.getCreatedAt(),
			PriceMapper.toEntity(domain.getPrice())
		);
	}

}
