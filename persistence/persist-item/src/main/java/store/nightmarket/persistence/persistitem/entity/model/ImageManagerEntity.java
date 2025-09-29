package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.state.EntityImageType;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;

@Getter
@Entity
@Table(name = "image_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageManagerEntity extends BaseUuidEntity {

	@Embedded
	private ImageEntity imageEntity;

	@Enumerated(EnumType.STRING)
	private EntityImageType entityImageType;

	@Column(name = "display_order")
	private int displayOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_owner_model")
	private ImageOwnerModelEntity imageOwnerModelEntity;

	private ImageManagerEntity(
		UUID id,
		ImageEntity imageEntity,
		EntityImageType entityImageType,
		int displayOrder,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		super(id);
		this.imageEntity = imageEntity;
		this.entityImageType = entityImageType;
		this.displayOrder = displayOrder;
		this.imageOwnerModelEntity = imageOwnerModelEntity;
	}

	private ImageManagerEntity(
		UUID id,
		LocalDateTime createdAt,
		ImageEntity imageEntity,
		EntityImageType entityImageType,
		int displayOrder,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		super(id, createdAt);
		this.imageEntity = imageEntity;
		this.entityImageType = entityImageType;
		this.displayOrder = displayOrder;
		this.imageOwnerModelEntity = imageOwnerModelEntity;
	}

	public static ImageManagerEntity newInstance(
		UUID id,
		ImageEntity imageEntity,
		EntityImageType entityImageType,
		int displayOrder,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		return new ImageManagerEntity(
			id,
			imageEntity,
			entityImageType,
			displayOrder,
			imageOwnerModelEntity
		);
	}

	public static ImageManagerEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		ImageEntity imageEntity,
		EntityImageType entityImageType,
		int displayOrder,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		return new ImageManagerEntity(
			id,
			createdAt,
			imageEntity,
			entityImageType,
			displayOrder,
			imageOwnerModelEntity
		);
	}

}
