package store.nightmarket.domain.payment.model.id;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class ProductId extends BaseId<UUID> {

	private final UUID id;

	public ProductId(UUID id) {
		this.id = id;
	}

}
