package store.nightmarket.application.appitem.out.preempt.mapper;

import store.nightmarket.domain.item.model.PreemptedProductVariant;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.PreemptedProductVariantId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.persistence.persistitem.entity.model.PreemptedProductVariantEntity;

public class PreemptedProductVariantMapper {

	public static PreemptedProductVariant toDomain(PreemptedProductVariantEntity entity) {
		return PreemptedProductVariant.newInstanceWithId(
			new PreemptedProductVariantId(entity.getId()),
			new OrderId(entity.getOrderId()),
			new ProductVariantId(entity.getProductVariantId()),
			entity.getQuantity(),
			entity.getExpiredAt()
		);
	}

	public static PreemptedProductVariantEntity toEntity(PreemptedProductVariant domain) {
		return PreemptedProductVariantEntity.newInstance(
			domain.getOrderId().getId(),
			domain.getProductVariantId().getId(),
			domain.getQuantity(),
			domain.getExpiredAt()
		);
	}

}
