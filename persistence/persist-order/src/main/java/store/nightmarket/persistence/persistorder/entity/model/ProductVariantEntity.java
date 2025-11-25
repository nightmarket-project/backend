package store.nightmarket.persistence.persistorder.entity.model;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistorder.entity.valueobject.PriceEntity;

@Getter
@Entity
@Table(name = "product_variant")
@NoArgsConstructor(access = PROTECTED)
public class ProductVariantEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "price", nullable = false)
	private PriceEntity priceEntity;

	public ProductVariantEntity(
		UUID id,
		PriceEntity priceEntity
	) {
		super(id);
		this.priceEntity = priceEntity;
	}

	public ProductVariantEntity(
		UUID id,
		LocalDateTime createdAt,
		PriceEntity priceEntity
	) {
		super(id, createdAt);
		this.priceEntity = priceEntity;
	}

}
