package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.ReadProductDto;
import store.nightmarket.application.appitem.in.dto.ReadProductListDto;
import store.nightmarket.application.appitem.in.dto.RegisterOptionDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductDto;
import store.nightmarket.application.appitem.in.dto.RegisterProductVariantDto;
import store.nightmarket.application.appitem.usecase.ReadProductListUseCase;
import store.nightmarket.application.appitem.usecase.ReadProductUseCase;
import store.nightmarket.application.appitem.usecase.RegisterOptionUseCase;
import store.nightmarket.application.appitem.usecase.RegisterProductUseCase;
import store.nightmarket.application.appitem.usecase.RegisterProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.dto.ReadProductListUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductUseCaseDto;
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

	private final ReadProductUseCase readProductUseCase;
	private final ReadProductListUseCase readProductListUseCase;
	private final RegisterProductUseCase registerProductUseCase;
	private final RegisterOptionUseCase registerOptionUseCase;
	private final RegisterProductVariantUseCase registerProductVariantUseCase;

	@GetMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public ReadProductListDto.Response readProductList(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "25") int size
	) {
		ReadProductListUseCaseDto.Output output = readProductListUseCase.execute(
			ReadProductListUseCaseDto.Input.builder()
				.page(page)
				.size(size)
				.build()
		);

		return ReadProductListDto.Response.builder()
			.contents(output.productPage().getContent().stream()
				.map(product ->
					ReadProductListDto.ProductInfo.builder()
						.productId(product.getProductId().getId())
						.name(product.getName().getValue())
						.description(product.getDescription())
						.price(product.getPrice().amount())
						.build()
				)
				.toList()
			)
			.currentPage(page)
			.numberOfElements(output.productPage().getNumberOfElements())
			.totalPage(output.productPage().getTotalPages())
			.totalElements(output.productPage().getTotalElements())
			.hasNext(output.productPage().hasNext())
			.build();
	}

	@GetMapping("/{productId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
	public ReadProductDto.Response readProduct(@PathVariable("productId") UUID productId) {
		ReadProductUseCaseDto.Output output = readProductUseCase.execute(
			ReadProductUseCaseDto.Input.builder()
				.productId(new ProductId(productId))
				.build()
		);

		return ReadProductDto.Response.builder()
			.productId(output.product().getProductId().getId())
			.name(output.product().getName().getValue())
			.description(output.product().getDescription())
			.price(output.product().getPrice().amount())
			.build();
	}

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
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
	@RequireRoles({"ROLE_ADMIN", "ROLE_SELLER"})
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
