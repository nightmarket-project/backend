package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.itemweb.state.ImageType;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ImageManagerEntity extends BaseUuidEntity {

	@Column(name = "display_order", nullable = false)
	private int displayOrder;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private ImageType type;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private ImageEntity imageEntity;

	protected ImageManagerEntity(
		UUID id,
		int displayOrder,
		ImageType type,
		ImageEntity imageEntity
	) {
		super(id);
		this.displayOrder = displayOrder;
		this.type = type;
		this.imageEntity = imageEntity;
	}
	
}
