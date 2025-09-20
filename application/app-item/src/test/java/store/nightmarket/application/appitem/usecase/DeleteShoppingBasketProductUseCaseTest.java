package store.nightmarket.application.appitem.usecase;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.out.DeleteShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.dto.DeleteShoppingBasketProductUseCaseDto;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;

class DeleteShoppingBasketProductUseCaseTest {

	private DeleteShoppingBasketProductUseCase deleteShoppingBasketProductUseCase;
	private DeleteShoppingBasketProductPort mockDeleteShoppingBasketProductPort;

	@BeforeEach
	void setUp() {
		mockDeleteShoppingBasketProductPort = mock(DeleteShoppingBasketProductPort.class);
		deleteShoppingBasketProductUseCase =
			new DeleteShoppingBasketProductUseCase(mockDeleteShoppingBasketProductPort);
	}

	@Test
	@DisplayName("장바구니 물건 삭제")
	void deleteShoppingBasketProduct() {
		// given
		ShoppingBasketProductId shoppingBasketProductId = new ShoppingBasketProductId(UUID.randomUUID());

		DeleteShoppingBasketProductUseCaseDto.Input input = DeleteShoppingBasketProductUseCaseDto.Input.builder()
			.shoppingBasketProductId(shoppingBasketProductId)
			.build();

		// when
		deleteShoppingBasketProductUseCase.execute(input);

		// then
		verify(mockDeleteShoppingBasketProductPort, times(1))
			.delete(shoppingBasketProductId);
	}

}
