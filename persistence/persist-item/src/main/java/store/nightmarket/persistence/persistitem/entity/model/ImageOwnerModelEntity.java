package store.nightmarket.persistence.persistitem.entity.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "owner_type")
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ImageOwnerModelEntity extends BaseUuidEntity {

	@Column(name = "image_owner_id", columnDefinition = "BINARY(16)", nullable = false)
	private UUID imageOwnerId;

	protected ImageOwnerModelEntity(
		UUID id,
		UUID imageOwnerId
	) {
		super(id);
		this.imageOwnerId = imageOwnerId;
	}

}
