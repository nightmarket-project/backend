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

@Getter
@Entity
@Table(name = "option_value")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionValueEntity extends BaseUuidEntity {

	@Embedded
	private NameEntity nameEntity;

	@Embedded
	private PriceEntity priceEntity;

	@Column(name = "display_order")
	private int displayOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroupEntity optionGroupEntity;

	private OptionValueEntity(
		UUID id,
		NameEntity nameEntity,
		PriceEntity priceEntity,
		int displayOrder,
		OptionGroupEntity optionGroupEntity
	) {
		super(id);
		this.nameEntity = nameEntity;
		this.priceEntity = priceEntity;
		this.displayOrder = displayOrder;
		this.optionGroupEntity = optionGroupEntity;
	}

	private OptionValueEntity(
		UUID id,
		LocalDateTime createdAt,
		NameEntity nameEntity,
		PriceEntity priceEntity,
		int displayOrder,
		OptionGroupEntity optionGroupEntity
	) {
		super(id, createdAt);
		this.nameEntity = nameEntity;
		this.priceEntity = priceEntity;
		this.displayOrder = displayOrder;
		this.optionGroupEntity = optionGroupEntity;
	}

	public static OptionValueEntity newInstance(
		UUID id,
		NameEntity nameEntity,
		PriceEntity priceEntity,
		int displayOrder,
		OptionGroupEntity optionGroupEntity
	) {
		return new OptionValueEntity(
			id,
			nameEntity,
			priceEntity,
			displayOrder,
			optionGroupEntity
		);
	}

	public static OptionValueEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		NameEntity nameEntity,
		PriceEntity priceEntity,
		int displayOrder,
		OptionGroupEntity optionGroupEntity
	) {
		return new OptionValueEntity(
			id,
			createdAt,
			nameEntity,
			priceEntity,
			displayOrder,
			optionGroupEntity
		);
	}

}
