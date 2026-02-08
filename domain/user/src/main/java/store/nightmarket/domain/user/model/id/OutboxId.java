package store.nightmarket.domain.user.model.id;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class OutboxId extends BaseId<Long> {

	private final Long id;

	public OutboxId(Long id) {
		this.id = id;
	}

}
