package store.nightmarket.common.out.persistence.jpa.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseAutoIncrementIdEntity implements Persistable<UUID> {

	@Id
	@Column(name = "id", columnDefinition = "BINARY(16)")
	protected UUID id;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	protected LocalDateTime updatedAt;

	@Override
	public boolean isNew() {
		return Objects.isNull(getId());
	}

}
