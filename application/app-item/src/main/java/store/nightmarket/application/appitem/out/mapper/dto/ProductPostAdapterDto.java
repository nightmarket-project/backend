package store.nightmarket.application.appitem.out.mapper.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.out.mapper.ProductPostMapper;
import store.nightmarket.application.appitem.out.mapper.ProductMapper;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.persistence.persistitem.entity.model.ProductPostEntity;

@Getter
@Builder
public class ProductPostAdapterDto {

	private final ProductPost productPost;
	private final Product product;

	public static ProductPostAdapterDto toDomain(ProductPostEntity entity) {
		return ProductPostAdapterDto.builder()
			.productPost(ProductPostMapper.toDomain(entity))
			.product(ProductMapper.toDomain(entity.getProductEntity()))
			.build();
	}

}
