package store.nightmarket.application.appitem.out.adaptor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.mapper.ProductMapper;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.persistence.persistitem.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class ReadProductJpaAdapter implements ReadProductPort {

	private final ProductRepository productRepository;

	@Override
	public Optional<Product> read(ProductId id) {
		return productRepository.findById(id.getId())
			.map(ProductMapper::toDomain);
	}

}
