package store.nightmarket.domain.order.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class ProductId extends BaseId<UUID> {

	private final UUID id;

	public ProductId(UUID id) {
		this.id = id;
	}

}
