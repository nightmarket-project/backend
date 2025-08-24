package store.nightmarket.application.appitem.in;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import store.nightmarket.application.appitem.in.dto.LastLayerDto;
import store.nightmarket.application.appitem.in.dto.MiddleLayerDto;
import store.nightmarket.application.appitem.in.dto.OptionLayerDto;
import store.nightmarket.application.appitem.out.dto.OptionGroupDto;
import store.nightmarket.application.appitem.out.dto.ProductVariantDto;
import store.nightmarket.application.appitem.out.dto.VariantOptionValueDto;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;

@Component
public class OptionLayerFactory {

	/**
	 * 옵션 그룹과 상품 Variant 정보를 바탕으로 계층형 OptionLayerDto 리스트를 생성합니다.
	 */
	public List<OptionLayerDto> create(List<OptionGroupDto> optionGroups, List<ProductVariantDto> productVariants) {
		if (optionGroups == null || optionGroups.isEmpty()) {
			return Collections.emptyList();
		}

		// 옵션 그룹 순서대로 정렬
		optionGroups.sort(Comparator.comparing(dto -> dto.getOptionGroup().getOrder()));
		List<OptionValue> firstGroupOptionValues = optionGroups.getFirst().getOptionValueList();
		firstGroupOptionValues.sort(Comparator.comparing(OptionValue::getOrder));

		// 옵션 그룹 개수에 따라 분기
		return switch (optionGroups.size()) {
			case 1 -> buildForOneGroup(firstGroupOptionValues, productVariants);
			case 2 -> buildForTwoGroups(firstGroupOptionValues, productVariants, optionGroups.get(1));
			case 3 -> buildForThreeGroups(
				firstGroupOptionValues, productVariants, optionGroups.get(1), optionGroups.get(2));
			// 참고: 4개 이상일 경우를 대비해 default 케이스 추가
			default -> throw new ProductException("지원하지 않는 옵션 그룹 개수입니다: " + optionGroups.size());
		};
	}

	// --- 1단 옵션 로직 ---
	private List<OptionLayerDto> buildForOneGroup(
		List<OptionValue> firstGroupValues,
		List<ProductVariantDto> productVariantDtoList
	) {
		List<OptionLayerDto> result = new ArrayList<>();
		for (OptionValue optionValue : firstGroupValues) {
			productVariantDtoList.stream()
				.filter(variant -> variantContainsOptionValue(
					variant,
					optionValue.getOptionValueId())
				)
				.map(variant -> new LastLayerDto(
					optionValue.getOptionValueId(),
					new Name(optionValue.getValue()),
					variant.getProductVariant().getProductVariantId())
				)
				.forEach(result::add);
		}
		return result;
	}

	// --- 2단 옵션 로직 ---
	private List<OptionLayerDto> buildForTwoGroups(
		List<OptionValue> firstGroupValues,
		List<ProductVariantDto> productVariantDtoList,
		OptionGroupDto secondGroup
	) {
		return firstGroupValues.stream()
			.map(firstOptionValue -> {
				List<ProductVariantDto> matchingVariantList = filterVariants(
					productVariantDtoList,
					firstOptionValue.getOptionValueId()
				);

				List<OptionLayerDto> secondLayer = matchingVariantList.stream()
					.map(variant -> {
						VariantOptionValueDto secondVariantOption = findVariantOptionForGroup(
							variant,
							secondGroup.getOptionGroup().getOptionGroupId()
						);
						return new LastLayerDto(
							secondVariantOption.getOptionValue().getOptionValueId(),
							new Name(secondVariantOption.getOptionValue().getValue()),
							variant.getProductVariant().getProductVariantId()
						);
					})
					.sorted(Comparator.comparing(
						dto -> findVariantOptionForGroup(
							findVariantById(productVariantDtoList, dto.getProductVariantId()),
							secondGroup.getOptionGroup().getOptionGroupId()
						).getOptionValue().getOrder())
					)
					.collect(Collectors.toList());

				return new MiddleLayerDto(
					firstOptionValue.getOptionValueId(),
					new Name(firstOptionValue.getValue()),
					secondLayer
				);
			})
			.collect(Collectors.toList());
	}

	// --- 3단 옵션 로직 ---
	private List<OptionLayerDto> buildForThreeGroups(
		List<OptionValue> firstGroupValues,
		List<ProductVariantDto> allVariants,
		OptionGroupDto secondGroup,
		OptionGroupDto thirdGroup
	) {
		return firstGroupValues.stream()
			.map(firstOptionValue -> {
				List<ProductVariantDto> matchingVariantList = filterVariants(allVariants,
					firstOptionValue.getOptionValueId());

				// 두 번째 옵션 그룹의 OptionValue를 키로 사용하여 Variant들을 그룹화
				Map<OptionValue, List<ProductVariantDto>> groupedBySecondOption = matchingVariantList.stream()
					.collect(Collectors.groupingBy(
						variant -> findVariantOptionForGroup(variant,
							secondGroup.getOptionGroup().getOptionGroupId()).getOptionValue()
					));

				// 그룹화된 맵을 정렬된 MiddleLayerDto 리스트로 변환
				List<MiddleLayerDto> secondLayer = groupedBySecondOption.entrySet().stream()
					.sorted(Map.Entry.comparingByKey(Comparator.comparing(OptionValue::getOrder)))
					.map(entry -> {
						OptionValue secondOptionValue = entry.getKey();
						List<ProductVariantDto> thirdLayerVariants = entry.getValue();

						List<OptionLayerDto> thirdLayer = thirdLayerVariants.stream()
							.map(variant -> {
								VariantOptionValueDto thirdVariantOption = findVariantOptionForGroup(variant,
									thirdGroup.getOptionGroup().getOptionGroupId());
								return new LastLayerDto(
									thirdVariantOption.getOptionValue().getOptionValueId(),
									new Name(thirdVariantOption.getOptionValue().getValue()),
									variant.getProductVariant().getProductVariantId()
								);
							})
							.sorted(Comparator.comparing(dto -> findVariantOptionForGroup(
								findVariantById(allVariants, dto.getProductVariantId()),
								thirdGroup.getOptionGroup().getOptionGroupId()).getOptionValue().getOrder()))
							.collect(Collectors.toList());

						return new MiddleLayerDto(
							secondOptionValue.getOptionValueId(),
							new Name(secondOptionValue.getValue()),
							thirdLayer
						);
					})
					.toList();

				return new MiddleLayerDto(
					firstOptionValue.getOptionValueId(),
					new Name(firstOptionValue.getValue()),
					new ArrayList<>(secondLayer) // 변환
				);
			})
			.collect(Collectors.toList());
	}

	/** 특정 OptionValue ID를 포함하는 Variant들만 필터링합니다. */
	private List<ProductVariantDto> filterVariants(List<ProductVariantDto> variants, OptionValueId valueId) {
		return variants.stream()
			.filter(variant -> variantContainsOptionValue(variant, valueId))
			.collect(Collectors.toList());
	}

	/** Variant가 특정 OptionValue ID를 포함하는지 확인합니다. */
	private boolean variantContainsOptionValue(ProductVariantDto variant, OptionValueId valueId) {
		return variant.getVariantOptionValueDtoList().stream()
			.anyMatch(vov -> vov.getOptionValue().getOptionValueId().equals(valueId));
	}

	/** Variant 내에서 특정 OptionGroup ID에 해당하는 VariantOptionValueDto를 찾습니다. */
	private VariantOptionValueDto findVariantOptionForGroup(ProductVariantDto variant, OptionGroupId groupId) {
		return variant.getVariantOptionValueDtoList().stream()
			.filter(vov -> vov.getOptionGroup().getOptionGroupId().equals(groupId))
			.findFirst()
			.orElseThrow(() -> new ProductException(
				"ProductVariant " + variant.getProductVariant().getProductVariantId() + " 에서 OptionGroup " + groupId
					+ " 에 해당하는 값을 찾을 수 없습니다."));
	}

	/** (정렬을 위해 추가) ID로 ProductVariantDto를 다시 찾습니다. */
	private ProductVariantDto findVariantById(List<ProductVariantDto> variants, ProductVariantId variantId) {
		return variants.stream()
			.filter(v -> v.getProductVariant().getProductVariantId().equals(variantId))
			.findFirst()
			.orElseThrow(() -> new ProductException("ID에 해당하는 Variant를 찾을 수 없습니다: " + variantId));
	}
}
