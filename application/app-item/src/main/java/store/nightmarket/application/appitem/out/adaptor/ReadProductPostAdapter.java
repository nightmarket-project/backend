package store.nightmarket.application.appitem.out.adaptor;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.itemweb.model.id.ProductPostId;
import store.nightmarket.persistence.persistitem.repository.ProductPostRepository;

@Component
@RequiredArgsConstructor
public class ReadProductPostAdapter implements ReadProductPostPort {

	private final ProductPostRepository productPostRepository;

	@Override
	public Page<ProductPostAdapterDto> findProductPostListByKeyword(String keyword, Pageable pageable) {
		return productPostRepository.findByKeywordContaining(keyword, pageable)
			.map(ProductPostAdapterDto::toDomain);
	}

	@Override
	public Optional<ProductPostAdapterDto> readFetch(ProductPostId productPostId) {
		return productPostRepository.findByPostId(productPostId.getId())
			.map(ProductPostAdapterDto::toDomain);
	}

}
