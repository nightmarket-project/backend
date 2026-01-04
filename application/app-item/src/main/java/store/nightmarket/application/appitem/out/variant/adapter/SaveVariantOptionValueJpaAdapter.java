package store.nightmarket.application.appitem.out.variant.adapter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.variant.mapper.VariantOptionValueMapper;
import store.nightmarket.application.appitem.out.variant.SaveVariantOptionValuePort;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.persistence.persistitem.entity.model.OptionGroupEntity;
import store.nightmarket.persistence.persistitem.entity.model.OptionValueEntity;
import store.nightmarket.persistence.persistitem.entity.model.ProductVariantEntity;
import store.nightmarket.persistence.persistitem.entity.model.VariantOptionValueEntity;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;
import store.nightmarket.persistence.persistitem.repository.OptionValueRepository;
import store.nightmarket.persistence.persistitem.repository.ProductVariantRepository;
import store.nightmarket.persistence.persistitem.repository.VariantOptionValueRepository;

@Component
@RequiredArgsConstructor
public class SaveVariantOptionValueJpaAdapter implements SaveVariantOptionValuePort {

	private final VariantOptionValueRepository variantOptionValueRepository;
	private final ProductVariantRepository productVariantRepository;
	private final OptionGroupRepository optionGroupRepository;
	private final OptionValueRepository optionValueRepository;

	@Override
	public void saveAll(List<VariantOptionValue> variantOptionValueList) {

		ProductVariantEntity productVariantEntity = productVariantRepository
			.findById(variantOptionValueList.getFirst().getProductVariantId().getId())
			.orElseThrow();
		Map<UUID, OptionGroupEntity> optionGroupMap = getUuidOptionGroupEntityMap(variantOptionValueList);
		Map<UUID, OptionValueEntity> optionValueMap = getUuidOptionValueEntityMap(variantOptionValueList);

		List<VariantOptionValueEntity> entities =
			variantOptionValueList.stream()
				.map(v -> VariantOptionValueMapper.toEntity(
					v,
					productVariantEntity, // 이미 확보됨
					optionGroupMap.get(v.getOptionGroupId().getId()),
					optionValueMap.get(v.getOptionValueId().getId())
				))
				.toList();

		variantOptionValueRepository.saveAll(entities);

	}

	private Map<UUID, OptionGroupEntity> getUuidOptionGroupEntityMap(List<VariantOptionValue> variantOptionValueList) {
		List<UUID> optionGroupIds = variantOptionValueList.stream()
			.map(v -> v.getOptionGroupId().getId())
			.toList();

		return optionGroupRepository.findAllById(optionGroupIds).stream()
			.collect(Collectors.toMap(OptionGroupEntity::getId, Function.identity()));
	}

	private Map<UUID, OptionValueEntity> getUuidOptionValueEntityMap(List<VariantOptionValue> variantOptionValueList) {
		List<UUID> optionValueIds = variantOptionValueList.stream()
			.map(v -> v.getOptionValueId().getId())
			.toList();

		return optionValueRepository.findAllById(optionValueIds).stream()
			.collect(Collectors.toMap(OptionValueEntity::getId, Function.identity()));
	}

}
