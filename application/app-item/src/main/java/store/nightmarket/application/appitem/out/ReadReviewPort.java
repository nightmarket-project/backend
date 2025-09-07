package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ReviewAdapterDto;

public interface ReadReviewPort {

	List<ReviewAdapterDto> read(UUID productPostId);

}
