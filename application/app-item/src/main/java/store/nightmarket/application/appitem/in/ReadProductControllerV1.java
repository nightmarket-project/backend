package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.OptionGroupListControllerDto;
import store.nightmarket.application.appitem.usecase.ReadOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadOptionGroupUseCaseDto;
import store.nightmarket.domain.item.valueobject.Name;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ReadProductControllerV1 {

	private final ReadOptionGroupUseCase readOptionGroupUseCase;

	@GetMapping("/{productId}/options")
	public OptionGroupListControllerDto.Response readProductPostOption(@PathVariable UUID productId) {
		ReadOptionGroupUseCaseDto.Output output = readOptionGroupUseCase.execute(productId);

		return OptionGroupListControllerDto.Response.builder()
			.optionGroupListControllerDtoList(
				output.optionGroupDtoList().stream()
					.map(optionGroupDto ->
						OptionGroupListControllerDto.OptionGroupControllerDto.builder()
							.optionGroupId(optionGroupDto.getOptionGroup().getOptionGroupId())
							.name(optionGroupDto.getOptionGroup().getName())
							.displayOrder(optionGroupDto.getOptionGroup().getOrder())
							.optionValueControllerDtoList(
								optionGroupDto.getOptionValueList().stream()
									.map(optionValue ->
										OptionGroupListControllerDto.OptionValueControllerDto.builder()
											.optionValueId(optionValue.getOptionValueId())
											.name(new Name(optionValue.getValue()))
											.price(optionValue.getPrice())
											.displayOrder(optionValue.getOrder())
											.build()
									).toList()
							)
							.build()
					).toList()
			)
			.build();
	}
}
