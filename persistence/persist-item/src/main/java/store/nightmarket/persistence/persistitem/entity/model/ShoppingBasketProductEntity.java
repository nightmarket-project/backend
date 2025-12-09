package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.QuantityEntity;

@Getter
@Entity
@Table(name = "shopping_basket_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingBasketProductEntity extends BaseUuidEntity {

	@Column(name = "user_id", nullable = false)
	private UUID userId;

	@Embedded
	private NameEntity nameEntity;

	@Embedded
	private QuantityEntity quantityEntity;

	@Embedded
	private PriceEntity priceEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant")
	private ProductVariantEntity productVariantEntity;

	private ShoppingBasketProductEntity(
		UUID id,
		UUID userId,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity
	) {
		super(id);
		this.userId = userId;
		this.nameEntity = nameEntity;
		this.quantityEntity = quantityEntity;
		this.priceEntity = priceEntity;
		this.productVariantEntity = productVariantEntity;
	}

	private ShoppingBasketProductEntity(
		UUID id,
		LocalDateTime createdAt,
		UUID userId,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity
	) {
		super(id, createdAt);
		this.userId = userId;
		this.nameEntity = nameEntity;
		this.quantityEntity = quantityEntity;
		this.priceEntity = priceEntity;
		this.productVariantEntity = productVariantEntity;

	}

	public static ShoppingBasketProductEntity newInstance(
		UUID id,
		UUID userId,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity
	) {
		return new ShoppingBasketProductEntity(
			id,
			userId,
			nameEntity,
			quantityEntity,
			priceEntity,
			productVariantEntity
		);
	}

	public static ShoppingBasketProductEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		UUID userId,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity
	) {
		return new ShoppingBasketProductEntity(
			id,
			createdAt,
			userId,
			nameEntity,
			quantityEntity,
			priceEntity,
			productVariantEntity
		);
	}

}
