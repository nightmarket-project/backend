package store.nightmarket.itemweb.model.id;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ReviewId extends ImageOwnerId {

	public ReviewId(UUID id) {
		super(id);
	}

}
