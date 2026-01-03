package store.nightmarket.application.appitem.in;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.auth.RequireRoles;
import store.nightmarket.application.appitem.auth.UserSession;
import store.nightmarket.application.appitem.config.resolver.AuthorizedUser;
import store.nightmarket.application.appitem.in.dto.ModifyShoppingBasketProductDto;
import store.nightmarket.application.appitem.in.dto.ReadShoppingBasketDto;
import store.nightmarket.application.appitem.in.dto.SaveShoppingBasketProductDto;
import store.nightmarket.application.appitem.usecase.DeleteShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.ModifyShoppingBasketQuantityUseCase;
import store.nightmarket.application.appitem.usecase.ReadShoppingBasketUseCase;
import store.nightmarket.application.appitem.usecase.SaveShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.dto.DeleteShoppingBasketProductUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ModifyShoppingBasketQuantityUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadShoppingBasketUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.SaveShoppingBasketProductUseCaseDto;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.model.id.ProductPostId;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class ShoppingBasketProductControllerV1 {

	private final SaveShoppingBasketProductUseCase saveShoppingBasketProductUseCase;
	private final DeleteShoppingBasketProductUseCase deleteShoppingBasketProductUseCase;
	private final ModifyShoppingBasketQuantityUseCase modifyShoppingBasketQuantityUseCase;
	private final ReadShoppingBasketUseCase readShoppingBasketUseCase;

	@GetMapping
	public ReadShoppingBasketDto.Response readShoppingBasket(@AuthorizedUser UserSession userSession) {
		ReadShoppingBasketUseCaseDto.Input input = ReadShoppingBasketUseCaseDto.Input.builder()
			.userId(new UserId(UUID.fromString(userSession.userId())))
			.build();

		ReadShoppingBasketUseCaseDto.Output output = readShoppingBasketUseCase.execute(input);

		List<ShoppingBasketProduct> shoppingBasketProductList = output.shoppingBasketProductList();
		Map<UUID, ImageManager> imageManagerMap = output.imageManagerList().stream()
			.collect(Collectors.toMap(imageManager -> imageManager.getImageOwnerId().getId(), Function.identity()));
		Map<ProductVariantId, ProductPostId> variantIdProductPostIdMap = output.variantIdProductPostIdMap();

		return ReadShoppingBasketDto.Response.builder()
			.shoppingBasket(
				shoppingBasketProductList.stream()
					.map(shoppingBasketProduct -> ReadShoppingBasketDto.ShoppingBasketProductInfo.builder()
						.shoppingBasketId(shoppingBasketProduct.getShoppingBasketProductId().getId())
						.productVariantId(shoppingBasketProduct.getProductVariantId().getId())
						.name(shoppingBasketProduct.getName().getValue())
						.price(shoppingBasketProduct.getUnitPrice().amount())
						.quantity(shoppingBasketProduct.getQuantity().value())
						.imageUrl(
							imageManagerMap.get(
									variantIdProductPostIdMap.get(shoppingBasketProduct.getProductVariantId()).getId())
								.getImage()
								.imageUrl())
						.productPostId(
							variantIdProductPostIdMap.get(shoppingBasketProduct.getProductVariantId()).getId())
						.build()
					)
					.toList()
			)
			.build();
	}

	@PostMapping
	@RequireRoles({"ROLE_ADMIN", "ROLE_USER", "ROLE_BUYER"})
	public void saveShoppingBasketProduct(@RequestBody SaveShoppingBasketProductDto.Request request,
		@AuthorizedUser UserSession userSession) {
		saveShoppingBasketProductUseCase.execute(
			SaveShoppingBasketProductUseCaseDto.Input.builder()
				.productVariantId(new ProductVariantId(request.productVariantId()))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.name(new Name(request.name()))
				.price(new Price(request.price()))
				.quantity(new Quantity(request.quantity()))
				.build()
		);
	}

	@DeleteMapping("/{cartId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_USER", "ROLE_BUYER"})
	public void deleteShoppingBasketProduct(@PathVariable("cartId") UUID cartId,
		@AuthorizedUser UserSession userSession) {
		deleteShoppingBasketProductUseCase.execute(
			DeleteShoppingBasketProductUseCaseDto.Input.builder()
				.shoppingBasketProductId(new ShoppingBasketProductId(cartId))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

	@PatchMapping("/{cartId}")
	@RequireRoles({"ROLE_ADMIN", "ROLE_USER", "ROLE_BUYER"})
	public void modifyShoppingBasketQuantity(
		@PathVariable("cartId") UUID cartId,
		@RequestBody ModifyShoppingBasketProductDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		modifyShoppingBasketQuantityUseCase.execute(
			ModifyShoppingBasketQuantityUseCaseDto.Input.builder()
				.shoppingBasketProductId(new ShoppingBasketProductId(cartId))
				.quantity(new Quantity(request.quantity()))
				.userId(new UserId(UUID.fromString(userSession.userId())))
				.build()
		);
	}

}
