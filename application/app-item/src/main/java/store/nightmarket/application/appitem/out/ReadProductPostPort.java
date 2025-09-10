package store.nightmarket.application.appitem.out;

import java.util.Optional;

import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public interface ReadProductPostPort {

	Optional<ProductPostAdapterDto> readFetch(ProductPostId id);

	default ProductPostAdapterDto readOrThrowFetch(ProductPostId id) {
		return readFetch(id)
			.orElseThrow(() -> new ItemWebException("Not found ProductPost"));
	}

}
