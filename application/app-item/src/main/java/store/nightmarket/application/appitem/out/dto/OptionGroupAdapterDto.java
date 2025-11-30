package store.nightmarket.application.appitem.out.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.application.appitem.out.mapper.OptionGroupMapper;
import store.nightmarket.application.appitem.out.mapper.OptionValueMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;

// TODO : AdaptorDto 네이밍 수정 필요(AdaptorDto 모두)
@Builder
@Getter
public class OptionGroupAdapterDto {

	private final OptionGroup optionGroup;
	private final List<OptionValue> optionValueList;

	public static OptionGroupAdapterDto toDomain(OptionGroupEntity entity) {
		return OptionGroupAdapterDto.builder()
			.optionGroup(OptionGroupMapper.toDomain(entity))
			.optionValueList(
				entity.getOptionValueEntityList().stream()
					.map(OptionValueMapper::toDomain)
					.toList()
			).build();
	}

}
