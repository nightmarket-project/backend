package store.nightmarket.application.appitem.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.DeleteProductPort;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.persistence.persistitem.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class DeleteProductJpaAdapter implements DeleteProductPort {

	private final ProductRepository productRepository;

	@Override
	public void delete(ProductId productId) {
		productRepository.deleteById(productId.getId());
	}

}
