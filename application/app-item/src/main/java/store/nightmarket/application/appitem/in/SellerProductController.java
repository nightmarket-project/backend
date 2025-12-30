package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.RegisterOptionDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductVariantDto;
import store.nightmarket.application.appitem.usecase.DeleteOptionGroupUseCase;
import store.nightmarket.application.appitem.usecase.DeleteOptionValueUseCase;
import store.nightmarket.application.appitem.usecase.RegisterOptionUseCase;
import store.nightmarket.application.appitem.usecase.RegisterProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.dto.DeleteOptionGroupUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.DeleteOptionValueUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.RegisterOptionUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.RegisterProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;

@RestController
@RequestMapping("api/v1/seller/product")
@RequiredArgsConstructor
public class SellerProductController {

	private final RegisterOptionUseCase registerOptionUseCase;
	private final RegisterProductVariantUseCase registerProductVariantUseCase;
	private final DeleteOptionGroupUseCase deleteOptionGroupUseCase;
	private final DeleteOptionValueUseCase deleteOptionValueUseCase;

	@PostMapping("/option")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerOption(
		@RequestBody RegisterOptionDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		registerOptionUseCase.execute(
			RegisterOptionUseCaseDto.Input.builder()
				.productId(new ProductId(request.productId()))
				.userId(new UserId(UUID.fromString(userSession.userId())))
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

	@DeleteMapping("/option/{optionGroupId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void deleteOptionGroup(
		@PathVariable("optionGroupId") UUID optionGroupId,
		@AuthorizedUser UserSession userSession
	) {
		deleteOptionGroupUseCase.execute(
			DeleteOptionGroupUseCaseDto.Input.builder()
				.optionGroupId(new OptionGroupId(optionGroupId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

	@DeleteMapping("/option/{optionGroupId}/value/{optionValueId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void deleteOptionValue(
		@PathVariable("optionGroupId") UUID optionGroupId,
		@PathVariable("optionValueId") UUID optionValueId,
		@AuthorizedUser UserSession userSession
	) {
		deleteOptionValueUseCase.execute(
			DeleteOptionValueUseCaseDto.Input.builder()
				.optionGroupId(new OptionGroupId(optionGroupId))
				.optionValueId(new OptionValueId(optionValueId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

	@PostMapping("/variant")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerProductVariant(
		@RequestBody RegisterProductVariantDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		registerProductVariantUseCase.execute(
			RegisterProductVariantUseCaseDto.Input.builder()
				.productId(new ProductId(request.productId()))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.SKUCode(request.SKUCode())
				.quantity(new Quantity(request.quantity()))
				.optionCombinationList(
					request.optionCombinationList().stream()
						.map(optionCombination ->
							RegisterProductVariantUseCaseDto.OptionCombination.builder()
								.optionGroupId(new OptionGroupId(optionCombination.optionGroupId()))
								.optionValueId(new OptionValueId(optionCombination.optionValueId()))
								.build()
						)
						.toList()
				)
				.build()
		);
	}

}
