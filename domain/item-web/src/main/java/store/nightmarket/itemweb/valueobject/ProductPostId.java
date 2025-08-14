package store.nightmarket.itemweb.valueobject;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ProductPostId extends ImageOwnerId {

	public ProductPostId(UUID id) {
		super(id);
	}

}
