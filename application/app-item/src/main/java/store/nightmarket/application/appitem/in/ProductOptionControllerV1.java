package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.ReadOptionGroupDto;
import store.nightmarket.application.appitem.in.dto.RegisterOptionGroupDto;
import store.nightmarket.application.appitem.in.dto.RegisterOptionValueDto;
import store.nightmarket.application.appitem.usecase.option.DeleteOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.option.DeleteOptionValueUseCase;
import store.nightmarket.application.appitem.usecase.option.ReadOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.option.RegisterOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.option.RegisterOptionValueUseCase;
import store.nightmarket.application.appitem.usecase.option.dto.DeleteOptionGroupUseCaseDto;
import store.nightmarket.application.appitem.usecase.option.dto.DeleteOptionValueUseCaseDto;
import store.nightmarket.application.appitem.usecase.option.dto.ReadOptionGroupUseCaseDto;
import store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionUseCaseDto;
import store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionValueUseCaseDto;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

@RestController
@RequestMapping("api/v1/products/{productId}/options")
@RequiredArgsConstructor
public class ProductOptionControllerV1 {

	private final ReadOptionGroupUseCase readOptionGroupUseCase;
	private final RegisterOptionGroupUseCase registerOptionGroupUseCase;
	private final RegisterOptionValueUseCase registerOptionValueUseCase;
	private final DeleteOptionGroupUseCase deleteOptionGroupUseCase;
	private final DeleteOptionValueUseCase deleteOptionValueUseCase;

	@GetMapping
	public ReadOptionGroupDto.Response readProductPostOption(@PathVariable("productId") UUID productId) {
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

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerOptionGroup(
		@PathVariable("productId") UUID productId,
		@RequestBody RegisterOptionGroupDto.Request request
	) {
		registerOptionGroupUseCase.execute(
			RegisterOptionUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
				.name(new Name(request.name()))
				.displayOrder(request.displayOrder())
				.optionValueDtoList(
					request.optionValueDtoList().stream()
						.map(requestList ->
							RegisterOptionUseCaseDto.OptionValueDto.builder()
								.name(new Name(requestList.name()))
								.price(new Price(requestList.price()))
								.displayOrder(requestList.displayOrder())
								.build()
						)
						.toList()
				)
				.build()
		);
	}

	@PostMapping("/{optionGroupId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerOptionValue(
		@PathVariable("productId") UUID productId,
		@PathVariable("optionGroupId") UUID optionGroupId,
		@AuthorizedUser UserSession userSession,
		@RequestBody RegisterOptionValueDto.Request request
	) {
		registerOptionValueUseCase.execute(
			RegisterOptionValueUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
				.optionGroupId(new OptionGroupId(optionGroupId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.name(new Name(request.name()))
				.price(new Price(request.price()))
				.displayOrder(request.displayOrder())
				.build()
		);
	}

	@DeleteMapping("/{optionGroupId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void deleteOptionGroup(
		@PathVariable("productId") UUID productId,
		@PathVariable("optionGroupId") UUID optionGroupId,
		@AuthorizedUser UserSession userSession
	) {
		deleteOptionGroupUseCase.execute(
			DeleteOptionGroupUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
				.optionGroupId(new OptionGroupId(optionGroupId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

	@DeleteMapping("/{optionGroupId}/values/{optionValueId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void deleteOptionValue(
		@PathVariable("productId") UUID productId,
		@PathVariable("optionGroupId") UUID optionGroupId,
		@PathVariable("optionValueId") UUID optionValueId,
		@AuthorizedUser UserSession userSession
	) {
		deleteOptionValueUseCase.execute(
			DeleteOptionValueUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
				.optionGroupId(new OptionGroupId(optionGroupId))
				.optionValueId(new OptionValueId(optionValueId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

}
