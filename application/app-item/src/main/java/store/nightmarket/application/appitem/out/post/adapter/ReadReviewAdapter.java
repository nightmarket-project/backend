package store.nightmarket.application.appitem.out.post.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.post.ReadReviewPort;
import store.nightmarket.application.appitem.out.post.mapper.dto.ReviewAdapterDto;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.persistence.persistitem.repository.ReviewRepository;

@Component
@RequiredArgsConstructor
public class ReadReviewAdapter implements ReadReviewPort {

	private final ReviewRepository reviewRepository;

	@Override
	public List<ReviewAdapterDto> read(ProductPostId productPostId) {
		return reviewRepository.findByProductPostId(productPostId.getId()).stream()
			.map(ReviewAdapterDto::toDomain)
			.toList();
	}

}
