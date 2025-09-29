package store.nightmarket.domain.order.valueobject;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class ProductVariantId extends BaseId<UUID> {

	private final UUID id;

	public ProductVariantId(UUID id) {
		this.id = id;
	}

}
