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
import store.nightmarket.persistence.persistitem.entity.valueobject.PriceEntity;

@Getter
@Entity
@Table(name = "option_value")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionValueEntity extends BaseUuidEntity {

	@Column(name = "name")
	private String name;

	@Embedded
	private PriceEntity priceEntity;

	@Column(name = "display_order")
	private int displayOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroupEntity optionGroupEntity;

	private OptionValueEntity(
		UUID id,
		String value,
		PriceEntity priceEntity,
		int displayOrder,
		OptionGroupEntity optionGroupEntity
	) {
		super(id);
		this.name = name;
		this.priceEntity = priceEntity;
		this.displayOrder = displayOrder;
		this.optionGroupEntity = optionGroupEntity;
	}

	public static OptionValueEntity newInstance(
		UUID id,
		String name,
		PriceEntity priceEntity,
		int displayOrder,
		OptionGroupEntity optionGroupEntity
	) {
		return new OptionValueEntity(
			id,
			name,
			priceEntity,
			displayOrder,
			optionGroupEntity
		);
	}

}
