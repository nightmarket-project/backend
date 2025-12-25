package store.nightmarket.application.appitem.usecase;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.dto.SaveShoppingBasketProductUseCaseDto;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;

class SaveShoppingBasketProductUseCaseTest {

	private SaveShoppingBasketProductPort mockSaveShoppingBasketProductPort;
	private SaveShoppingBasketProductUseCase saveShoppingBasketProductUseCase;

	@BeforeEach
	void setUp() {
		mockSaveShoppingBasketProductPort = mock(SaveShoppingBasketProductPort.class);
		saveShoppingBasketProductUseCase = new SaveShoppingBasketProductUseCase(mockSaveShoppingBasketProductPort);
	}

	@Test
	@DisplayName("장바구니에 물건 담기")
	void putProductIntoShoppingBasket() {
		// given
		SaveShoppingBasketProductUseCaseDto.Input input = SaveShoppingBasketProductUseCaseDto.Input.builder()
			.productVariantId(new ProductVariantId(UUID.randomUUID()))
			.userId(new UserId(UUID.randomUUID()))
			.name(new Name("상품"))
			.price(new Price(BigInteger.valueOf(10000)))
			.quantity(new Quantity(BigInteger.TEN))
			.build();

		// when
		saveShoppingBasketProductUseCase.execute(input);

		// then
		verify(mockSaveShoppingBasketProductPort, times(1))
			.save(any(ShoppingBasketProduct.class));
	}

}
