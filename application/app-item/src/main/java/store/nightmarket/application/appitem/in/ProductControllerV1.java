package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ReadOptionGroupDto;
import store.nightmarket.application.appitem.in.dto.ReadProductVariantDto;
import store.nightmarket.application.appitem.usecase.ReadOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadOptionGroupUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductVariantUseCaseDto;
import store.nightmarket.domain.item.valueobject.ProductId;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 {

	private final ReadOptionGroupUseCase readOptionGroupUseCase;
	private final ReadProductVariantUseCase readProductVariantUseCase;

	@GetMapping("/{productId}/options")
	public ReadOptionGroupDto.Response readProductPostOption(@PathVariable("productId") UUID productId) {
		ReadOptionGroupUseCaseDto.Output output = readOptionGroupUseCase.execute(new ProductId(productId));

		return ReadOptionGroupDto.Response.builder()
			.optionGroupInfoList(
				output.optionGroupAdapterDtoList().stream()
					.map(optionGroupDto ->
						ReadOptionGroupDto.OptionGroupInfo.builder()
							.optionGroupId(optionGroupDto.getOptionGroup().getOptionGroupId().getId())
							.name(optionGroupDto.getOptionGroup().getName().getValue())
							.displayOrder(optionGroupDto.getOptionGroup().getOrder())
							.optionValueInfoList(
								optionGroupDto.getOptionValueList().stream()
									.map(optionValue ->
										ReadOptionGroupDto.OptionValueInfo.builder()
											.optionValueId(optionValue.getOptionValueId().getId())
											.name(optionValue.getValue())
											.price(optionValue.getPrice().amount())
											.displayOrder(optionValue.getOrder())
											.build())
									.toList())
							.build())
					.toList())
			.build();
	}

	@GetMapping("/{productId}/combination")
	public ReadProductVariantDto.Response readProductPostProductVariant(@PathVariable("productId") UUID productId) {
		ReadProductVariantUseCaseDto.Output output = readProductVariantUseCase.execute(new ProductId(productId));

		return ReadProductVariantDto.Response.builder()
			.productVariantInfoList(
				output.productVariantAdapterDtoList().stream()
					.map(productVariantDto ->
						ReadProductVariantDto.ProductVariantInfo.builder()
							.productVariantId(productVariantDto.getProductVariant().getProductVariantId().getId())
							.variantOptionValueInfoList(
								productVariantDto.getVariantOptionValueAdapterDtoList().stream()
									.map(variantOptionValueDto ->
										ReadProductVariantDto.VariantOptionValueInfo.builder()
											.optionGroupId(
												variantOptionValueDto.getVariantOptionValue()
													.getOptionGroupId()
													.getId())
											.optionValueId(
												variantOptionValueDto.getVariantOptionValue()
													.getOptionValueId()
													.getId())
											.build())
									.toList())
							.build())
					.toList())
			.build();
	}

}
