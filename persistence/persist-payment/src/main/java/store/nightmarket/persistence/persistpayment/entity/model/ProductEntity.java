package store.nightmarket.persistence.persistpayment.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistpayment.entity.valueobject.PriceEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseUuidEntity {

	@Embedded
	private PriceEntity priceEntity;

	@OneToOne(mappedBy = "productEntity")
	private DetailPaymentRecordEntity detailPaymentRecordEntity;

	public ProductEntity(
		UUID id,
		PriceEntity priceEntity
	) {
		super(id);
		this.priceEntity = priceEntity;
	}

	public ProductEntity(
		UUID id,
		LocalDateTime createdAt,
		PriceEntity priceEntity
	) {
		super(id, createdAt);
		this.priceEntity = priceEntity;
	}

}
