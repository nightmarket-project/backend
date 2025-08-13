package store.nightmarket.persistence.persistitem.entity.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "name")
	private NameEntity name;

	@Column(name = "description")
	private String description;

	@Embedded
	@Column(name = "price")
	private PriceEntity priceEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_post_id")
	private ProductPostEntity productPostEntity;

	@OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
	private List<OptionGroupEntity> optionGroupEntityList = new ArrayList<>();

	@OneToMany(mappedBy = "productEntity", fetch = FetchType.LAZY)
	private List<ProductVariantEntity> productVariantEntityList = new ArrayList<>();

	private ProductEntity(
		NameEntity name,
		String description,
		PriceEntity priceEntity,
		ProductPostEntity productPostEntity
	) {
		this.name = name;
		this.description = description;
		this.priceEntity = priceEntity;
		this.productPostEntity = productPostEntity;
	}

	public static ProductEntity newInstance(
		NameEntity name,
		String description,
		PriceEntity priceEntity,
		ProductPostEntity productPostEntity
	) {
		return new ProductEntity(name,
			description,
			priceEntity,
			productPostEntity
		);
	}

}
