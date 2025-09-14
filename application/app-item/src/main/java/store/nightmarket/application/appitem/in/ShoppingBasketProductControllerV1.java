package store.nightmarket.application.appitem.in;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.SaveShoppingBasketProductDto;
import store.nightmarket.application.appitem.usecase.PutShoppingBasketProductUseCase;
import store.nightmarket.application.appitem.usecase.dto.PutShoppingBasketProductUseCaseDto;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class ShoppingBasketProductControllerV1 {

	private final PutShoppingBasketProductUseCase putShoppingBasketProductUseCase;

	@PostMapping
	public void saveShoppingBasketProduct(SaveShoppingBasketProductDto.Request request) {
		putShoppingBasketProductUseCase.execute(
			PutShoppingBasketProductUseCaseDto.Input.builder()
				.productVariantId(request.productVariantId())
				.userId(request.userId())
				.name(request.name())
				.price(request.price())
				.quantity(request.quantity())
				.build()
		);
	}

}
