package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadReviewPort;
import store.nightmarket.application.appitem.out.dto.ReviewDto;
import store.nightmarket.persistence.persistitem.repository.ReviewRepository;

@Component
@RequiredArgsConstructor
public class ReadReviewAdaptor implements ReadReviewPort {

	private final ReviewRepository reviewRepository;

	@Override
	public List<ReviewDto> read(UUID productPostId) {
		return reviewRepository.findByProductPostId(productPostId).stream()
			.map(ReviewDto::toDomain)
			.toList();
	}

}
