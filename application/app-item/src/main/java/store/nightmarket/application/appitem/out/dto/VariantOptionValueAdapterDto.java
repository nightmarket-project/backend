package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.VariantOptionValueMapper;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;

@Getter
@Builder
public class VariantOptionValueAdapterDto {

	private final VariantOptionValue variantOptionValue;

	public static VariantOptionValueAdapterDto toDomain(VariantOptionValueEntity entity) {
		return VariantOptionValueAdapterDto.builder()
			.variantOptionValue(VariantOptionValueMapper.toDomain(entity))
			.build();
	}

}
