package store.nightmarket.persistence.persistpayment.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.domain.payment.state.DetailPaymentState;

@Getter
@Entity
@Table(name = "detail_payment_record")
@NoArgsConstructor
public class DetailPaymentRecordEntity extends BaseUuidEntity {

	@OneToOne
	@JoinColumn(name = "product_id")
	private ProductEntity productEntity;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private DetailPaymentState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_record_id", nullable = false)
	private PaymentRecordEntity paymentRecordEntity;

	public DetailPaymentRecordEntity(
		ProductEntity productEntity,
		DetailPaymentState state
	) {
		this.productEntity = productEntity;
		this.state = state;
	}

}
