package store.nightmarket.application.appitem.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveProductPort;
import store.nightmarket.application.appitem.out.mapper.ProductMapper;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.persistence.persistitem.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class SaveProductJpaAdapter implements SaveProductPort {

	private final ProductRepository productRepository;

	@Override
	public void save(Product product) {
		productRepository.save(ProductMapper.toEntity(product));
	}

}
