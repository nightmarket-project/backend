package store.nightmarket.application.appitem.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.out.mapper.ProductPostMapper;
import store.nightmarket.domain.item.exception.ItemUserException;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.persistence.persistitem.entity.model.ProductEntity;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.repository.ProductPostRepository;
import store.nightmarket.persistence.persistitem.repository.ProductRepository;
import store.nightmarket.persistence.persistitem.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SaveProductPostJpaAdapter implements SaveProductPostPort {

	private final ProductPostRepository productPostRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;

	@Override
	public void save(ProductPost productPost) {
		ProductEntity productEntity = productRepository.findById(productPost.getProductId().getId())
			.orElseThrow(() -> new ProductException("Not Found Product"));

		UserEntity userEntity = userRepository.findById(productEntity.getUserId())
			.orElseThrow(() -> new ItemUserException("Not Found User"));

		productPostRepository.save(ProductPostMapper.toEntity(productPost, productEntity, userEntity));
	}

}
