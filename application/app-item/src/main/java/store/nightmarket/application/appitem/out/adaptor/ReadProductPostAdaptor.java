package store.nightmarket.application.appitem.out.adaptor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.itemweb.valueobject.ProductPostId;
import store.nightmarket.persistence.persistitem.repository.ProductPostRepository;

@Component
@RequiredArgsConstructor
public class ReadProductPostAdaptor implements ReadProductPostPort {

	private final ProductPostRepository productPostRepository;

	@Override
	public Optional<ProductPostAdapterDto> readFetch(ProductPostId id) {
		return productPostRepository.findByPostId(id.getId())
			.map(ProductPostAdapterDto::toDomain);
	}

}
