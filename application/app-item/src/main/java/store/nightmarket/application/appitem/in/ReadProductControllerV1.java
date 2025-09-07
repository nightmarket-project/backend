package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ReadOptionGroupResponseDto;
import store.nightmarket.application.appitem.in.dto.ReadProductVariantResponseDto;
import store.nightmarket.application.appitem.usecase.ReadOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadOptionGroupUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductVariantUseCaseDto;
import store.nightmarket.domain.item.valueobject.Name;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ReadProductControllerV1 {

	private final ReadOptionGroupUseCase readOptionGroupUseCase;
	private final ReadProductVariantUseCase readProductVariantUseCase;

	@GetMapping("/{productId}/options")
	public ReadOptionGroupResponseDto.Response readProductPostOption(@PathVariable UUID productId) {
		ReadOptionGroupUseCaseDto.Output output = readOptionGroupUseCase.execute(productId);

		return ReadOptionGroupResponseDto.Response.builder()
			.optionGroupListControllerDtoList(
				output.optionGroupDtoList().stream()
					.map(optionGroupDto ->
						ReadOptionGroupResponseDto.OptionGroupControllerDto.builder()
							.optionGroupId(optionGroupDto.getOptionGroup().getOptionGroupId())
							.name(optionGroupDto.getOptionGroup().getName())
							.displayOrder(optionGroupDto.getOptionGroup().getOrder())
							.optionValueControllerDtoList(
								optionGroupDto.getOptionValueList().stream()
									.map(optionValue ->
										ReadOptionGroupResponseDto.OptionValueControllerDto.builder()
											.optionValueId(optionValue.getOptionValueId())
											.name(new Name(optionValue.getValue()))
											.price(optionValue.getPrice())
											.displayOrder(optionValue.getOrder())
											.build())
									.toList())
							.build())
					.toList())
			.build();
	}

	@GetMapping("/{productId}/productVariants")
	public ReadProductVariantResponseDto.Response readProductPostProductVariant(@PathVariable UUID productId) {
		ReadProductVariantUseCaseDto.Output output = readProductVariantUseCase.execute(productId);

		return ReadProductVariantResponseDto.Response.builder()
			.productVariantControllerDtoList(
				output.productVariantDtoList().stream()
					.map(productVariantDto ->
						ReadProductVariantResponseDto.ProductVariantControllerDto.builder()
							.productVariantId(productVariantDto.getProductVariant().getProductVariantId())
							.variantOptionValueControllerDtoList(
								productVariantDto.getVariantOptionValueDtoList().stream()
									.map(variantOptionValueDto ->
										ReadProductVariantResponseDto.VariantOptionValueControllerDto.builder()
											.optionGroupId(
												variantOptionValueDto.getVariantOptionValue().getOptionGroupId())
											.optionValueId(
												variantOptionValueDto.getVariantOptionValue().getOptionValueId())
											.build())
									.toList())
							.build())
					.toList())
			.build();
	}

}
