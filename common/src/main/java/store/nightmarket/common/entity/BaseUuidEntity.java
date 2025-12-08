package store.nightmarket.common.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseUuidEntity extends BaseEntity implements Persistable<UUID> {

	@Id
	@Column(name = "id")
	protected UUID id;

	@Override
	public boolean isNew() {
		return createdAt == null;
	}

	protected BaseUuidEntity(UUID id) {
		this.id = id;
	}

	protected BaseUuidEntity(UUID id, LocalDateTime createdAt) {
		super(createdAt);
		this.id = id;
	}

}