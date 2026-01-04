package store.nightmarket.application.appitem.out.product.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.product.mapper.ProductMapper;
import store.nightmarket.application.appitem.out.product.SaveProductPort;
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
