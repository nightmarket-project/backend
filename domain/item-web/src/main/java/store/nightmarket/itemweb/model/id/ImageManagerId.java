package store.nightmarket.itemweb.model.id;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class ImageManagerId extends BaseId<UUID> {

	private final UUID id;

	public ImageManagerId(UUID id) {
		this.id = id;
	}

}
