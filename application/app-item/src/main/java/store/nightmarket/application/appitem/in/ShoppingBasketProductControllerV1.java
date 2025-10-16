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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ModifyShoppingBasketProductDto;
import store.nightmarket.application.appitem.in.dto.ReadShoppingBasketDto;
import store.nightmarket.application.appitem.in.dto.SaveShoppingBasketProductDto;
import store.nightmarket.application.appitem.usecase.DeleteShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.ModifyShoppingBasketQuantityUseCase;
import store.nightmarket.application.appitem.usecase.PutShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.ReadShoppingBasketUseCase;
import store.nightmarket.application.appitem.usecase.dto.DeleteShoppingBasketProductUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ModifyShoppingBasketQuantityUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.PutShoppingBasketProductUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ReadShoppingBasketUseCaseDto;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.valueobject.ProductPostId;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class ShoppingBasketProductControllerV1 {

	private final PutShoppingBasketProductUseCase putShoppingBasketProductUseCase;
	private final DeleteShoppingBasketProductUseCase deleteShoppingBasketProductUseCase;
	private final ModifyShoppingBasketQuantityUseCase modifyShoppingBasketQuantityUseCase;
	private final ReadShoppingBasketUseCase readShoppingBasketUseCase;

	@GetMapping
	public ReadShoppingBasketDto.Response readShoppingBasket(@RequestParam("userId") UUID userId) {
		ReadShoppingBasketUseCaseDto.Input input = ReadShoppingBasketUseCaseDto.Input.builder()
			.userId(new UserId(userId))
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
						.productVariantId(shoppingBasketProduct.getVariantId().getId())
						.name(shoppingBasketProduct.getName().getValue())
						.price(shoppingBasketProduct.getUnitPrice().amount())
						.quantity(shoppingBasketProduct.getQuantity().value())
						.imageUrl(
							imageManagerMap.get(
									variantIdProductPostIdMap.get(shoppingBasketProduct.getVariantId()).getId())
								.getImage()
								.imageUrl())
						.productPostId(variantIdProductPostIdMap.get(shoppingBasketProduct.getVariantId()).getId())
						.build()
					)
					.toList()
			)
			.build();
	}

	@PostMapping
	public void saveShoppingBasketProduct(@RequestBody SaveShoppingBasketProductDto.Request request) {
		putShoppingBasketProductUseCase.execute(
			PutShoppingBasketProductUseCaseDto.Input.builder()
				.productVariantId(new ProductVariantId(request.productVariantId()))
				.userId(new UserId(request.userId()))
				.name(new Name(request.name()))
				.price(new Price(request.price()))
				.quantity(new Quantity(request.quantity()))
				.build()
		);
	}

	@DeleteMapping("/{cartId}")
	public void deleteShoppingBasketProduct(@PathVariable("cartId") UUID cartId) {
		deleteShoppingBasketProductUseCase.execute(
			DeleteShoppingBasketProductUseCaseDto.Input.builder()
				.shoppingBasketProductId(new ShoppingBasketProductId(cartId))
				.build()
		);
	}

	@PatchMapping("/{cartId}")
	public void modifyShoppingBasketQuantity(
		@PathVariable("cartId") UUID cartId,
		@RequestBody ModifyShoppingBasketProductDto.Request request
	) {
		modifyShoppingBasketQuantityUseCase.execute(
			ModifyShoppingBasketQuantityUseCaseDto.Input.builder()
				.shoppingBasketProductId(new ShoppingBasketProductId(cartId))
				.quantity(new Quantity(request.quantity()))
				.userId(new UserId(request.userId()))
				.build()
		);
	}

}
