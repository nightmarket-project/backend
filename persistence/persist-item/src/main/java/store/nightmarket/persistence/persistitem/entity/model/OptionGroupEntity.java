package store.nightmarket.persistence.persistitem.entity.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

@Getter
@Entity
@Table(name = "option_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionGroupEntity extends BaseUuidEntity {

	@Column(name = "product_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID productId;

	@Embedded
	@Column(name = "name")
	private NameEntity nameEntity;

	@Column(name = "order")
	private int order;

	@OneToMany(mappedBy = "optionGroupEntity", fetch = FetchType.LAZY)
	private List<OptionValueEntity> optionValueEntityList = new ArrayList<>();

	private OptionGroupEntity(
		UUID id,
		UUID productId,
		NameEntity nameEntity,
		int order
	) {
		super(id);
		this.productId = productId;
		this.nameEntity = nameEntity;
		this.order = order;
	}

	public static OptionGroupEntity newInstance(
		UUID id,
		UUID productId,
		NameEntity nameEntity,
		int order
	) {
		return new OptionGroupEntity(
			id,
			productId,
			nameEntity,
			order
		);
	}

}
