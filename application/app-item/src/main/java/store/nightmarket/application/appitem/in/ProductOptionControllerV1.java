package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ReadOptionGroupDto;
import store.nightmarket.application.appitem.usecase.option.ReadOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.option.dto.ReadOptionGroupUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;

@RestController
@RequestMapping("api/v1/products/{productId}/options")
@RequiredArgsConstructor
public class ProductOptionControllerV1 {

	private final ReadOptionGroupUseCase readOptionGroupUseCase;

	@GetMapping
	public ReadOptionGroupDto.Response readProductOption(@PathVariable("productId") UUID productId) {
		ReadOptionGroupUseCaseDto.Output output = readOptionGroupUseCase.execute(new ProductId(productId));

		return ReadOptionGroupDto.Response.builder()
			.optionGroupList(
				output.optionGroupAdapterDtoList().stream()
					.map(optionGroupDto ->
						ReadOptionGroupDto.OptionGroupInfo.builder()
							.optionGroupId(optionGroupDto.getOptionGroup().getOptionGroupId().getId())
							.name(optionGroupDto.getOptionGroup().getName().getValue())
							.displayOrder(optionGroupDto.getOptionGroup().getOrder())
							.optionValueList(
								optionGroupDto.getOptionValueList().stream()
									.map(optionValue ->
										ReadOptionGroupDto.OptionValueInfo.builder()
											.optionValueId(optionValue.getOptionValueId().getId())
											.name(optionValue.getName().getValue())
											.price(optionValue.getPrice().amount())
											.displayOrder(optionValue.getOrder())
											.build())
									.toList())
							.build())
					.toList())
			.build();
	}

}
