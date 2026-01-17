package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ReadProductVariantDto;
import store.nightmarket.application.appitem.usecase.variant.ReadProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.variant.dto.ReadProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;

@RestController
@RequestMapping("api/v1/products/{productId}/variants")
@RequiredArgsConstructor
public class ProductVariantControllerV1 {

	private final ReadProductVariantUseCase readProductVariantUseCase;

	@GetMapping
	public ReadProductVariantDto.Response readProductVariant(@PathVariable("productId") UUID productId) {
		ReadProductVariantUseCaseDto.Output output = readProductVariantUseCase.execute(new ProductId(productId));

		return ReadProductVariantDto.Response.builder()
			.productVariantList(
				output.productVariantAdapterDtoList().stream()
					.map(productVariantDto ->
						ReadProductVariantDto.ProductVariantInfo.builder()
							.productVariantId(productVariantDto.getProductVariant().getProductVariantId().getId())
							.SKUCode(productVariantDto.getProductVariant().getSKUCode())
							.quantity(productVariantDto.getProductVariant().getQuantity().value().longValue())
							.variantOptionValue(
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
