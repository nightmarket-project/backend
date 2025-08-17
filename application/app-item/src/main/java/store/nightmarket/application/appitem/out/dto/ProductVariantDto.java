package store.nightmarket.application.appitem.out.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.ProductVariantMapper;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;

@Getter
@Builder
public class ProductVariantDto {

	private final ProductVariant productVariant;
	private final List<VariantOptionValueDto> variantOptionValueDtoList;

	public static ProductVariantDto toDomain(ProductVariantEntity entity) {
		return ProductVariantDto.builder()
			.productVariant(ProductVariantMapper.toDomain(entity))
			.variantOptionValueDtoList(
				entity.getVariantOptionValueEntityList().stream()
					.map(VariantOptionValueDto::toDomain)
					.toList()
			).build();
	}

}
