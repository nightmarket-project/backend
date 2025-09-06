package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.VariantOptionValueMapper;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;

@Getter
@Builder
public class VariantOptionValueDto {

	private final VariantOptionValue variantOptionValue;

	public static VariantOptionValueDto toDomain(VariantOptionValueEntity entity) {
		return VariantOptionValueDto.builder()
			.variantOptionValue(VariantOptionValueMapper.toDomain(entity))
			.build();
	}

}
