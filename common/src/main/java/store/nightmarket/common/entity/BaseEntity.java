package store.nightmarket.common.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	protected LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	protected LocalDateTime updatedAt;

	protected BaseEntity(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}

