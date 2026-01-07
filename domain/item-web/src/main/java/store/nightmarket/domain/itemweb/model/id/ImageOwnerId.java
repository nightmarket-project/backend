package store.nightmarket.domain.itemweb.model.id;

import java.util.UUID;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class ImageOwnerId extends BaseId<UUID> {

	private final UUID id;

	public ImageOwnerId(UUID id) {
		this.id = id;
	}

}
