package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
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

	@Column(name = "product_id", nullable = false)
	private UUID productId;

	@Embedded
	private NameEntity name;

	@Column(name = "display_order")
	private int displayOrder;

	@OneToMany(mappedBy = "optionGroupEntity", fetch = FetchType.LAZY)
	private List<OptionValueEntity> optionValueEntityList = new ArrayList<>();

	private OptionGroupEntity(
		UUID id,
		UUID productId,
		NameEntity name,
		int displayOrder
	) {
		super(id);
		this.productId = productId;
		this.name = name;
		this.displayOrder = displayOrder;
	}

	private OptionGroupEntity(
		UUID id,
		LocalDateTime createdAt,
		UUID productId,
		NameEntity name,
		int displayOrder
	) {
		super(id, createdAt);
		this.productId = productId;
		this.name = name;
		this.displayOrder = displayOrder;
	}

	public static OptionGroupEntity newInstance(
		UUID id,
		UUID productId,
		NameEntity name,
		int displayOrder
	) {
		return new OptionGroupEntity(
			id,
			productId,
			name,
			displayOrder
		);
	}

	public static OptionGroupEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		UUID productId,
		NameEntity name,
		int displayOrder
	) {
		return new OptionGroupEntity(
			id,
			createdAt,
			productId,
			name,
			displayOrder
		);
	}

}
