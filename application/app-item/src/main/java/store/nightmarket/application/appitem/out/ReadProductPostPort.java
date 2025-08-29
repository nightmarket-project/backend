package store.nightmarket.application.appitem.out;

import java.util.Optional;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ProductPostDto;
import store.nightmarket.itemweb.exception.ItemWebException;

public interface ReadProductPostPort {

	Optional<ProductPostDto> readFetch(UUID id);

	default ProductPostDto readOrThrowFetch(UUID id) {
		return readFetch(id)
			.orElseThrow(() -> new ItemWebException("Not found ProductPost"));
	}

}
