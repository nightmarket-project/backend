package store.nightmarket.domain.user.valueobject;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class UserId extends BaseId<UUID> {

	private UUID id;

	protected UserId() {
	}

	public UserId(UUID id) {
		this.id = id;
	}

}
