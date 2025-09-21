package store.nightmarket.application.appitem.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.ModifyShoppingBasketProductDto;
import store.nightmarket.application.appitem.in.dto.SaveShoppingBasketProductDto;
import store.nightmarket.application.appitem.usecase.DeleteShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.ModifyShoppingBasketQuantityUseCase;
import store.nightmarket.application.appitem.usecase.PutShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.dto.DeleteShoppingBasketProductUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.ModifyShoppingBasketQuantityUseCaseDto;
import store.nightmarket.application.appitem.usecase.dto.PutShoppingBasketProductUseCaseDto;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;

@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class ShoppingBasketProductControllerV1 {

	private final PutShoppingBasketProductUseCase putShoppingBasketProductUseCase;
	private final DeleteShoppingBasketProductUseCase deleteShoppingBasketProductUseCase;
	private final ModifyShoppingBasketQuantityUseCase modifyShoppingBasketQuantityUseCase;

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
