package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ReviewDto;

public interface ReadReviewPort {

	List<ReviewDto> read(UUID productPostId);
	
}
