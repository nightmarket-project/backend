package store.nightmarket.application.appitem.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import store.nightmarket.application.appitem.out.mapper.dto.ProductPostAdapterDto;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public interface ReadProductPostPort {

	Page<ProductPostAdapterDto> findProductPostListByKeyword(String keyword, Pageable pageable);

	Optional<ProductPostAdapterDto> readFetch(ProductPostId id);

	default ProductPostAdapterDto readOrThrowFetch(ProductPostId id) {
		return readFetch(id)
			.orElseThrow(() -> new ProductPostException("Not found ProductPost"));
	}

	List<ProductPost> readRefreshProductPost(LocalDateTime now);

}
