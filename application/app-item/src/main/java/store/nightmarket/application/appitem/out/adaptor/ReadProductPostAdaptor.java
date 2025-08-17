package store.nightmarket.application.appitem.out.adaptor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.mapper.ProductPostMapper;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostDto;
import store.nightmarket.itemweb.model.ProductPost;
import store.nightmarket.persistence.persistitem.repository.ProductPostRepository;

@Component
@RequiredArgsConstructor
public class ReadProductPostAdaptor implements ReadProductPostPort {

	private final ProductPostRepository productPostRepository;

	@Override
	public Optional<ProductPost> read(UUID id) {
		return productPostRepository.findByPostId(id)
			.map(ProductPostMapper::toDomain);
	}

	@Override
	public Optional<ProductPostDto> readFetchWithProductAndReviewsAndReplies(UUID id) {
		return productPostRepository.findByPostId(id)
			.map(ProductPostDto::toDomain);
	}

}
