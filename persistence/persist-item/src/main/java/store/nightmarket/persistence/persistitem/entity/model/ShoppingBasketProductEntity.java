package store.nightmarket.persistence.persistitem.entity.model;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant")
	private ProductVariantEntity productVariantEntity;
	
	private ShoppingBasketProductEntity(
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		ProductVariantEntity productVariantEntity
	) {
		this.nameEntity = nameEntity;
		this.quantityEntity = quantityEntity;
		this.productVariantEntity = productVariantEntity;
	}

	public static ShoppingBasketProductEntity newInstance(
		NameEntity nameEntity,
		QuantityEntity quantityEntity,
		ProductVariantEntity productVariantEntity
	) {
		return new ShoppingBasketProductEntity(
			nameEntity,
			quantityEntity,
			productVariantEntity
		);
	}

}
