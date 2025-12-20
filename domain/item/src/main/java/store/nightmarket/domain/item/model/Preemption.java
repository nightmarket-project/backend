package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.PreemptionId;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Getter
public class Preemption extends BaseModel<PreemptionId> {

	private final OrderId orderId;
	private final ProductVariantId productVariantId;
	private final Long quantity;
	private final LocalDateTime expiredAt;

	private Preemption(
		OrderId orderId,
		ProductVariantId productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		this.orderId = orderId;
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.expiredAt = expiredAt;
	}

	private Preemption(
		PreemptionId id,
		OrderId orderId,
		ProductVariantId productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		super(id);
		this.orderId = orderId;
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.expiredAt = expiredAt;
	}

	public static Preemption newInstance(
		OrderId orderId,
		ProductVariantId productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new Preemption(
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

	public static Preemption newInstanceWithId(
		PreemptionId id,
		OrderId orderId,
		ProductVariantId productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new Preemption(
			id,
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

	public PreemptionId getPreemptionId() {
		return internalId();
	}

}
