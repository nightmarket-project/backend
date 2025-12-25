package store.nightmarket.domain.item.model;

import java.time.LocalDateTime;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.PreemptedProductVariantId;
import store.nightmarket.domain.item.model.id.ProductVariantId;

@Getter
public class PreemptedProductVariant extends BaseModel<PreemptedProductVariantId> {

	private final OrderId orderId;
	private final ProductVariantId productVariantId;
	private final Long quantity;
	private final LocalDateTime expiredAt;

	private PreemptedProductVariant(
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

	private PreemptedProductVariant(
		PreemptedProductVariantId id,
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

	public static PreemptedProductVariant newInstance(
		OrderId orderId,
		ProductVariantId productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new PreemptedProductVariant(
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

	public static PreemptedProductVariant newInstanceWithId(
		PreemptedProductVariantId id,
		OrderId orderId,
		ProductVariantId productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new PreemptedProductVariant(
			id,
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

	public PreemptedProductVariantId getPreemptedProductVariantId() {
		return internalId();
	}

}
