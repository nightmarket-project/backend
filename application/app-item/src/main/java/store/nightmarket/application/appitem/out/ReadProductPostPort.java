package store.nightmarket.application.appitem.out;

import java.util.Optional;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.itemweb.exception.ItemWebException;

public interface ReadProductPostPort {

	Optional<ProductPostAdapterDto> readFetch(UUID id);

	default ProductPostAdapterDto readOrThrowFetch(UUID id) {
		return readFetch(id)
			.orElseThrow(() -> new ItemWebException("Not found ProductPost"));
	}

}
