package store.nightmarket.application.appitem.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.model.id.ProductPostId;

public interface ReadProductPostPort {

	Page<ProductPostAdapterDto> findProductPostListByKeyword(String keyword, Pageable pageable);

	Optional<ProductPostAdapterDto> readFetch(ProductPostId id);

	default ProductPostAdapterDto readOrThrowFetch(ProductPostId id) {
		return readFetch(id)
			.orElseThrow(() -> new ItemWebException("Not found ProductPost"));
	}

}
