package store.nightmarket.application.appitem.out.mapper.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.out.mapper.ProductVariantMapper;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;

@Getter
@Builder
public class ProductVariantAdapterDto {

	private final ProductVariant productVariant;
	private final List<VariantOptionValueAdapterDto> variantOptionValueAdapterDtoList;

	public static ProductVariantAdapterDto toDomain(ProductVariantEntity entity) {
		return ProductVariantAdapterDto.builder()
			.productVariant(ProductVariantMapper.toDomain(entity))
			.variantOptionValueAdapterDtoList(
				entity.getVariantOptionValueEntityList().stream()
					.map(VariantOptionValueAdapterDto::toDomain)
					.toList()
			).build();
	}

}
