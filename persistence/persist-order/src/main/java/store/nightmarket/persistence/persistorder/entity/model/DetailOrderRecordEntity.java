package store.nightmarket.persistence.persistorder.entity.model;

import static lombok.AccessLevel.*;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.persistence.persistorder.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "detail_order_record")
@NoArgsConstructor(access = PROTECTED)
public class DetailOrderRecordEntity extends BaseUuidEntity {

	@Column(name = "product_variant_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID productVariantId;

	@Embedded
	private QuantityEntity quantity;

	@Enumerated(EnumType.STRING)
	@Column(name = "state", nullable = false)
	private DetailOrderState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_record_id", nullable = false)
	private OrderRecordEntity orderRecordEntity;

	public DetailOrderRecordEntity(
		UUID id,
		UUID productVariantId,
		QuantityEntity quantity,
		DetailOrderState state,
		OrderRecordEntity orderRecordEntity
	) {
		super(id);
		this.productVariantId = productVariantId;
		this.quantity = quantity;
		this.state = state;
		this.orderRecordEntity = orderRecordEntity;
	}

}
