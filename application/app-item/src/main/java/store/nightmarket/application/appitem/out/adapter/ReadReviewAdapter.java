package store.nightmarket.application.appitem.out.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadReviewPort;
import store.nightmarket.application.appitem.out.mapper.dto.ReviewAdapterDto;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
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
