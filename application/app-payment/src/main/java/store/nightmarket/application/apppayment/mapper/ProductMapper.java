package store.nightmarket.application.apppayment.mapper;

import store.nightmarket.domain.payment.model.Product;
import store.nightmarket.domain.payment.valueobject.ProductId;
import store.nightmarket.persistence.persistpayment.entity.model.ProductEntity;

public class ProductMapper {

	public static Product toDomain(ProductEntity entity) {
		return Product.newInstance(
			new ProductId(entity.getId()),
			PriceMapper.toDomain(entity.getPriceEntity())
		);
	}

	public static ProductEntity toEntity(Product domain) {
		return new ProductEntity(
			PriceMapper.toEntity(domain.getPrice())
		);
	}

}
