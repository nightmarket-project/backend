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
import store.nightmarket.persistence.persistitem.entity.state.ImageType;
import store.nightmarket.persistence.persistitem.entity.valueobject.ImageEntity;

@Getter
@Entity
@Table(name = "image_manager")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageManagerEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "image")
	private ImageEntity imageEntity;

	@Column(name = "image_type")
	private ImageType imageType;

	@Column(name = "display_oreder")
	private int displayOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_owner_model")
	private ImageOwnerModelEntity imageOwnerModelEntity;

	private ImageManagerEntity(
		UUID id,
		ImageEntity imageEntity,
		ImageType imageType,
		int displayOrder,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		super(id);
		this.imageEntity = imageEntity;
		this.imageType = imageType;
		this.displayOrder = displayOrder;
		this.imageOwnerModelEntity = imageOwnerModelEntity;
	}

	public static ImageManagerEntity newInstance(
		UUID id,
		ImageEntity imageEntity,
		ImageType imageType,
		int displayOrder,
		ImageOwnerModelEntity imageOwnerModelEntity
	) {
		return new ImageManagerEntity(
			id,
			imageEntity,
			imageType,
			displayOrder,
			imageOwnerModelEntity
		);
	}

}
