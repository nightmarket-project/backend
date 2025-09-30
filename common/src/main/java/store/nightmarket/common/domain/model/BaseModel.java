package store.nightmarket.common.domain.model;

import java.time.LocalDateTime;

public abstract class BaseModel<IdType> {

	private IdType id;
	private LocalDateTime createdAt;

	protected BaseModel() {
	}

	public BaseModel(IdType id) {
		this.id = id;
	}

	public BaseModel(IdType id, LocalDateTime createdAt) {
		this.id = id;
		this.createdAt = createdAt;
	}

	protected IdType internalId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

}
