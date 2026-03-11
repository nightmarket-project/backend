package store.nightmarket.domain.itemweb.model.id;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class SchedulePostId extends BaseId<UUID> {

	private final UUID id;

	public SchedulePostId(UUID id) {
		this.id = id;
	}

}
