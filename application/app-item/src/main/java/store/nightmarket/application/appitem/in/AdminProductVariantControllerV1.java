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
import store.nightmarket.application.appitem.in.dto.ReadVariantOptionValueDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductVariantDto;
import store.nightmarket.application.appitem.usecase.variant.DeleteProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.variant.ReadVariantOptionValueUseCase;
import store.nightmarket.application.appitem.usecase.variant.RegisterProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.variant.dto.DeleteProductVariantUseCaseDto;
import store.nightmarket.application.appitem.usecase.variant.dto.ReadVariantOptionValueUseCaseDto;
import store.nightmarket.application.appitem.usecase.variant.dto.RegisterProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

@RestController
@RequestMapping("api/v1/admin/products/{productId}/variants")
@RequiredArgsConstructor
public class AdminProductVariantControllerV1 {

	private final ReadVariantOptionValueUseCase readVariantOptionValueUseCase;
	private final RegisterProductVariantUseCase registerProductVariantUseCase;
	private final DeleteProductVariantUseCase deleteProductVariantUseCase;

	@GetMapping("/{variantId}/options")
	public ReadVariantOptionValueDto.Response readVariantOptionValue(
		@PathVariable("productId") UUID productId,
		@PathVariable("variantId") UUID productVariantId
	) {
		ReadVariantOptionValueUseCaseDto.Output output = readVariantOptionValueUseCase.execute(
			ReadVariantOptionValueUseCaseDto.Input.builder()
				.productVariantId(new ProductVariantId(productVariantId))
				.build()
		);

		return ReadVariantOptionValueDto.Response.builder()
			.variantOptionValueList(
				output.variantOptionValueList().stream()
					.map(variantOptionValue ->
						ReadVariantOptionValueDto.VariantOptionValueInfo.builder()
							.optionGroupId(variantOptionValue.getOptionGroupId().getId())
							.optionValueId(variantOptionValue.getOptionValueId().getId())
							.build()
					)
					.toList()
			)
			.build();
	}

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void registerProductVariant(
		@PathVariable("productId") UUID productId,
		@RequestBody RegisterProductVariantDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		registerProductVariantUseCase.execute(
			RegisterProductVariantUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
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

	@DeleteMapping("/{variantId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public void deleteProductVariant(
		@PathVariable("productId") UUID productId,
		@PathVariable("variantId") UUID variantId,
		@AuthorizedUser UserSession userSession
	) {
		deleteProductVariantUseCase.execute(
			DeleteProductVariantUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
				.productVariantId(new ProductVariantId(variantId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

}
