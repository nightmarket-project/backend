package store.nightmarket.domain.item.model.id;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class PreemptedProductVariantId extends BaseId<Long> {

	private final Long id;

	public PreemptedProductVariantId(Long id) {
		this.id = id;
	}

}
