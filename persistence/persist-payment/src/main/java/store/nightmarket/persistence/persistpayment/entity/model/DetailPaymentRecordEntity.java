package store.nightmarket.persistence.persistpayment.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.domain.payment.state.DetailPaymentState;

@Getter
@Entity
@Table(name = "detail_payment_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailPaymentRecordEntity extends BaseUuidEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private DetailPaymentState state;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private ProductEntity productEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_record_id", nullable = false)
	private PaymentRecordEntity paymentRecordEntity;

	public DetailPaymentRecordEntity(
		UUID id,
		DetailPaymentState state,
		ProductEntity productEntity,
		PaymentRecordEntity paymentRecordEntity
	) {
		super(id);
		this.state = state;
		this.productEntity = productEntity;
		this.paymentRecordEntity = paymentRecordEntity;
	}

}
