package store.nightmarket.persistence.persistpayment.entity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistpayment.entity.valueobject.PriceEntity;

@Getter
@Entity
@NoArgsConstructor
public class ProductEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "price")
	private PriceEntity priceEntity;

	@OneToOne(mappedBy = "productEntity")
	private DetailPaymentRecordEntity detailPaymentRecordEntity;

	public ProductEntity(PriceEntity priceEntity) {
		this.priceEntity = priceEntity;
	}

}
