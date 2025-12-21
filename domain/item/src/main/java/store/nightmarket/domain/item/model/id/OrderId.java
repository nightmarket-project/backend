package store.nightmarket.domain.item.model.id;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class OrderId extends BaseId<UUID> {

	private final UUID id;

	public OrderId(UUID id) {
		this.id = id;
	}

}
