package store.nightmarket.application.appitem.out.mapper;

import store.nightmarket.domain.item.model.Preemption;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.PreemptionId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.persistence.persistitem.entity.model.PreemptionEntity;

public class PreemptionMapper {

	public static Preemption toDomain(PreemptionEntity entity) {
		return Preemption.newInstanceWithId(
			new PreemptionId(entity.getId()),
			new OrderId(entity.getOrderId()),
			new ProductVariantId(entity.getProductVariantId()),
			entity.getQuantity(),
			entity.getExpiredAt()
		);
	}

	public static PreemptionEntity toEntity(Preemption domain) {
		return PreemptionEntity.newInstance(
			domain.getOrderId().getId(),
			domain.getProductVariantId().getId(),
			domain.getQuantity(),
			domain.getExpiredAt()
		);
	}

}
