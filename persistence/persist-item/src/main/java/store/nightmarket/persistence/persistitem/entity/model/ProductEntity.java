package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
	private NameEntity nameEntity;

	@Column(name = "description")
	private String description;

	@Embedded
	@Column(name = "price")
	private PriceEntity priceEntity;

	private ProductEntity(
		UUID id,
		NameEntity nameEntity,
		String description,
		PriceEntity priceEntity
	) {
		super(id);
		this.nameEntity = nameEntity;
		this.description = description;
		this.priceEntity = priceEntity;
	}

	public static ProductEntity newInstance(
		UUID id,
		NameEntity nameEntity,
		String description,
		PriceEntity priceEntity
	) {
		return new ProductEntity(
			id,
			nameEntity,
			description,
			priceEntity
		);
	}

}
