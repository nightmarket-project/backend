package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseAutoIncrementIdEntity;

@Getter
@Entity
@Table(name = "preemption")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreemptionEntity extends BaseAutoIncrementIdEntity {

	@Column(name = "order_id", nullable = false)
	private UUID orderId;

	@Column(name = "product_variant_id", nullable = false)
	private UUID productVariantId;

	@Column(name = "quantity", nullable = false)
	private Long quantity;

	@Column(name = "expired_at", nullable = false)
	private LocalDateTime expiredAt;

	private PreemptionEntity(
		UUID orderId,
		UUID productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		this.orderId = orderId;
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.expiredAt = expiredAt;
	}

	private PreemptionEntity(
		Long id,
		UUID orderId,
		UUID productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		super(id);
		this.orderId = orderId;
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.expiredAt = expiredAt;
	}

	public static PreemptionEntity newInstance(
		UUID orderId,
		UUID productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new PreemptionEntity(
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

	public static PreemptionEntity newInstanceWithId(
		Long id,
		UUID orderId,
		UUID productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new PreemptionEntity(
			id,
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

}
