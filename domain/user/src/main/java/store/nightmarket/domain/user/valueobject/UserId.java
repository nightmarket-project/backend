package store.nightmarket.domain.user.valueobject;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class UserId extends BaseId<UUID> implements Serializable {

	private final UUID id;

	public UserId(UUID id) {
		this.id = id;
	}

}
