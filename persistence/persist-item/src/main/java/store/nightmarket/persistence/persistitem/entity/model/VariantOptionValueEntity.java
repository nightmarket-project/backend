package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "variant_option_value")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VariantOptionValueEntity extends BaseUuidEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_variant_id")
	private ProductVariantEntity productVariantEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_group_id")
	private OptionGroupEntity optionGroupEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_value_id")
	private OptionValueEntity optionValueEntity;

	private VariantOptionValueEntity(
		UUID id,
		ProductVariantEntity productVariantEntity,
		OptionGroupEntity optionGroupEntity,
		OptionValueEntity optionValueEntity
	) {
		super(id);
		this.productVariantEntity = productVariantEntity;
		this.optionGroupEntity = optionGroupEntity;
		this.optionValueEntity = optionValueEntity;
	}

	private VariantOptionValueEntity(
		UUID id,
		LocalDateTime createdAt,
		ProductVariantEntity productVariantEntity,
		OptionGroupEntity optionGroupEntity,
		OptionValueEntity optionValueEntity
	) {
		super(id, createdAt);
		this.productVariantEntity = productVariantEntity;
		this.optionGroupEntity = optionGroupEntity;
		this.optionValueEntity = optionValueEntity;
	}

	public static VariantOptionValueEntity newInstance(
		UUID id,
		ProductVariantEntity productVariantEntity,
		OptionGroupEntity optionGroupEntity,
		OptionValueEntity optionValueEntity
	) {
		return new VariantOptionValueEntity(
			id,
			productVariantEntity,
			optionGroupEntity,
			optionValueEntity
		);
	}

	public static VariantOptionValueEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		ProductVariantEntity productVariantEntity,
		OptionGroupEntity optionGroupEntity,
		OptionValueEntity optionValueEntity
	) {
		return new VariantOptionValueEntity(
			id,
			createdAt,
			productVariantEntity,
			optionGroupEntity,
			optionValueEntity
		);
	}

}
