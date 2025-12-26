package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.RegisterOptionDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductVariantDto;
import store.nightmarket.application.appitem.usecase.RegisterOptionUseCase;
import store.nightmarket.application.appitem.usecase.RegisterProductUseCase;
import store.nightmarket.application.appitem.usecase.RegisterProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.dto.RegisterOptionUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.RegisterProductUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.RegisterProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;

@RestController
@RequestMapping("api/v1/admin/product")
@RequiredArgsConstructor
public class AdminProductControllerV1 {

	private final RegisterProductUseCase registerProductUseCase;
	private final RegisterOptionUseCase registerOptionUseCase;
	private final RegisterProductVariantUseCase registerProductVariantUseCase;

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_BUYER"})
	public void registerProduct(@RequestBody RegisterProductDto.Request request) {
		registerProductUseCase.execute(
			RegisterProductUseCaseDto.Input.builder()
				.name(new Name(request.name()))
				.description(request.description())
				.price(new Price(request.price()))
				.build()
		);
	}

	@PostMapping("/option")
	@RequireRoles({"ROLE_ADMIN", "ROLE_BUYER"})
	public void registerOption(@RequestBody RegisterOptionDto.Request request) {
		registerOptionUseCase.execute(
			RegisterOptionUseCaseDto.Input.builder()
				.productId(new ProductId(request.productId()))
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

	@PostMapping("/variant")
	@RequireRoles({"ROLE_ADMIN", "ROLE_BUYER"})
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
