package store.nightmarket.application.appitem.out;

import java.util.Optional;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ProductPostDto;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.model.ProductPost;

public interface ReadProductPostPort {

	Optional<ProductPost> read(UUID id);

	Optional<ProductPostDto> readFetchWithProductAndReviewsAndReplies(UUID id);

	default ProductPost readOrThrow(UUID id) {
		return read(id)
			.orElseThrow(() -> new ItemWebException("Not found ProductPost"));
	}

	default ProductPostDto readOrThrowFetchWithProductAndReviewAndReplies(UUID id) {
		return readFetchWithProductAndReviewsAndReplies(id)
			.orElseThrow(() -> new ItemWebException("Not found ProductPost"));
	}

}
