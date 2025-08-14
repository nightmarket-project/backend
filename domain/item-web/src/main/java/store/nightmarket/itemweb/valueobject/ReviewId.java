package store.nightmarket.itemweb.valueobject;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ReviewId extends ImageOwnerId {

	public ReviewId(UUID id) {
		super(id);
	}

}
