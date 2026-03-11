package store.nightmarket.application.appitem.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.out.mapper.ProductPostMapper;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.repository.ProductPostRepository;
import store.nightmarket.persistence.persistitem.repository.ProductRepository;

@Component
@RequiredArgsConstructor
public class SaveProductPostJpaAdapter implements SaveProductPostPort {

	private final ProductPostRepository productPostRepository;
	private final ProductRepository productRepository;

	@Override
	public void save(ProductPost productPost) {
		ProductEntity productEntity = productRepository.findById(productPost.getProductId().getId())
			.orElseThrow(() -> new ProductException("Not Found Product"));

		productPostRepository.save(ProductPostMapper.toEntity(productPost, productEntity));
	}

}
