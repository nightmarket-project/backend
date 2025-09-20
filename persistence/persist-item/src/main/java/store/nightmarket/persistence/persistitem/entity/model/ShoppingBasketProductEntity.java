package store.nightmarket.persistence.persistitem.entity.model;

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

	@Embedded
	@Column(name = "name")
	private NameEntity nameEntity;

	@Embedded
	@Column(name = "quantity")
	private QuantityEntity quantityEntity;

	@Embedded
	@Column(name = "price")
	private PriceEntity priceEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant")
	private ProductVariantEntity productVariantEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
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

}
