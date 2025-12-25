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
@Table(name = "preempted_product_variant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PreemptedProductVariantEntity extends BaseAutoIncrementIdEntity {

	@Column(name = "order_id", nullable = false)
	private UUID orderId;

	@Column(name = "product_variant_id", nullable = false)
	private UUID productVariantId;

	@Column(name = "quantity", nullable = false)
	private Long quantity;

	@Column(name = "expired_at", nullable = false)
	private LocalDateTime expiredAt;

	private PreemptedProductVariantEntity(
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

	private PreemptedProductVariantEntity(
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

	public static PreemptedProductVariantEntity newInstance(
		UUID orderId,
		UUID productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new PreemptedProductVariantEntity(
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

	public static PreemptedProductVariantEntity newInstanceWithId(
		Long id,
		UUID orderId,
		UUID productVariantId,
		Long quantity,
		LocalDateTime expiredAt
	) {
		return new PreemptedProductVariantEntity(
			id,
			orderId,
			productVariantId,
			quantity,
			expiredAt
		);
	}

}
