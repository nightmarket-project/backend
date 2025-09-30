package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

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

	@Embedded
	private NameEntity nameEntity;

	@Embedded
	private QuantityEntity quantityEntity;

	@Embedded
	private PriceEntity priceEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant")
	private ProductVariantEntity productVariantEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;

	private ShoppingBasketProductEntity(
		UUID id,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity,
		UserEntity userEntity
	) {
		super(id);
		this.nameEntity = nameEntity;
		this.quantityEntity = quantityEntity;
		this.priceEntity = priceEntity;
		this.productVariantEntity = productVariantEntity;
		this.userEntity = userEntity;
	}

	private ShoppingBasketProductEntity(
		UUID id,
		LocalDateTime createdAt,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity,
		UserEntity userEntity
	) {
		super(id, createdAt);
		this.nameEntity = nameEntity;
		this.quantityEntity = quantityEntity;
		this.priceEntity = priceEntity;
		this.productVariantEntity = productVariantEntity;
		this.userEntity = userEntity;
	}

	public static ShoppingBasketProductEntity newInstance(
		UUID id,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity,
		UserEntity userEntity
	) {
		return new ShoppingBasketProductEntity(
			id,
			nameEntity,
			quantityEntity,
			priceEntity,
			productVariantEntity,
			userEntity
		);
	}

	public static ShoppingBasketProductEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		PriceEntity priceEntity,
		ProductVariantEntity productVariantEntity,
		UserEntity userEntity
	) {
		return new ShoppingBasketProductEntity(
			id,
			createdAt,
			nameEntity,
			quantityEntity,
			priceEntity,
			productVariantEntity,
			userEntity
		);
	}

}
