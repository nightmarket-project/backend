package store.nightmarket.application.appitem.out.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.mapper.OptionGroupMapper;
import store.nightmarket.application.appitem.mapper.OptionValueMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;

@Builder
@Getter
public class OptionGroupDto {

	private final OptionGroup optionGroup;
	private final List<OptionValue> optionValueList;

	public static OptionGroupDto toDomain(OptionGroupEntity entity) {
		return OptionGroupDto.builder()
			.optionGroup(OptionGroupMapper.toDomain(entity))
			.optionValueList(
				entity.getOptionValueEntityList().stream()
					.map(OptionValueMapper::toDomain)
					.toList()
			).build();
	}

}
