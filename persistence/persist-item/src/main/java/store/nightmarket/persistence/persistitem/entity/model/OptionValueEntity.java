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

	@Column(name = "value")
	private String value;

	@Embedded
	@Column(name = "price")
	private PriceEntity priceEntity;

	@Column(name = "order")
	private int order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroupEntity optionGroupEntity;

	private OptionValueEntity(
		UUID id,
		String value,
		PriceEntity priceEntity,
		int order,
		OptionGroupEntity optionGroupEntity
	) {
		super(id);
		this.value = value;
		this.priceEntity = priceEntity;
		this.order = order;
		this.optionGroupEntity = optionGroupEntity;
	}

	public static OptionValueEntity newInstance(
		UUID id,
		String value,
		PriceEntity priceEntity,
		int order,
		OptionGroupEntity optionGroupEntity
	) {
		return new OptionValueEntity(
			id,
			value,
			priceEntity,
			order,
			optionGroupEntity
		);
	}

}
