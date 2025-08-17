package store.nightmarket.application.appitem.out.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.OptionGroupMapper;
import store.nightmarket.application.appitem.mapper.OptionValueMapper;
import store.nightmarket.application.appitem.mapper.VariantOptionValueMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;

@Getter
@Builder
public class VariantOptionValueDto {

	private final VariantOptionValue variantOptionValue;
	private final OptionGroup optionGroup;
	private final OptionValue optionValue;

	public static VariantOptionValueDto toDomain(VariantOptionValueEntity entity) {
		return VariantOptionValueDto.builder()
			.variantOptionValue(VariantOptionValueMapper.toDomain(entity))
			.optionGroup(OptionGroupMapper.toDomain(entity.getOptionGroupEntity()))
			.optionValue(OptionValueMapper.toDomain(entity.getOptionValueEntity()))
			.build();
	}

}
