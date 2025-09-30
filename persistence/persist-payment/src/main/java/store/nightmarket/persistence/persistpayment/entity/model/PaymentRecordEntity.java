package store.nightmarket.persistence.persistpayment.entity.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "payment_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentRecordEntity extends BaseUuidEntity {

	@Column(name = "user_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	@OneToMany(mappedBy = "paymentRecordEntity", fetch = FetchType.LAZY)
	private List<DetailPaymentRecordEntity> detailPaymentRecordEntityList = new ArrayList<>();

	public PaymentRecordEntity(
		UUID id,
		UUID userId,
		List<DetailPaymentRecordEntity> detailPaymentRecordEntityList
	) {
		super(id);
		this.userId = userId;
		this.detailPaymentRecordEntityList = detailPaymentRecordEntityList;
	}

	public PaymentRecordEntity(
		UUID id,
		LocalDateTime createdAt,
		UUID userId,
		List<DetailPaymentRecordEntity> detailPaymentRecordEntityList
	) {
		super(id, createdAt);
		this.userId = userId;
		this.detailPaymentRecordEntityList = detailPaymentRecordEntityList;
	}

}
