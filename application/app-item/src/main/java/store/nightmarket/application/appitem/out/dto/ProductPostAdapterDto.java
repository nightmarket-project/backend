package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.ProductMapper;
import store.nightmarket.application.appitem.mapper.ProductPostMapper;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.itemweb.model.ProductPost;
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
