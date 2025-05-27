package store.nightmarket.common.entity;

import java.util.UUID;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseUuidEntity extends BaseEntity implements Persistable<UUID> {

	@Id
	@Column(name = "id", columnDefinition = "BINARY(16)")
	protected UUID id;

	@Override
	public boolean isNew() {
		return createdAt == null;
	}

}