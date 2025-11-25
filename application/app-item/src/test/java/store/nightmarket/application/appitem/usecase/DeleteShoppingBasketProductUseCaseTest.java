package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.DeleteShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.dto.DeleteShoppingBasketProductUseCaseDto;
import store.nightmarket.domain.item.exception.ShoppingBasketException;
import store.nightmarket.domain.item.valueobject.ShoppingBasketProductId;
import store.nightmarket.domain.item.valueobject.UserId;

class DeleteShoppingBasketProductUseCaseTest {

	private DeleteShoppingBasketProductUseCase deleteShoppingBasketProductUseCase;
	private ReadShoppingBasketProductPort mockReadShoppingBasketProductPort;
	private DeleteShoppingBasketProductPort mockDeleteShoppingBasketProductPort;

	@BeforeEach
	void setUp() {
		mockDeleteShoppingBasketProductPort = mock(DeleteShoppingBasketProductPort.class);
		mockReadShoppingBasketProductPort = mock(ReadShoppingBasketProductPort.class);
		deleteShoppingBasketProductUseCase =
			new DeleteShoppingBasketProductUseCase(
				mockReadShoppingBasketProductPort,
				mockDeleteShoppingBasketProductPort
			);
	}

	@Test
	@DisplayName("장바구니 물건 삭제")
	void deleteShoppingBasketProduct() {
		// given
		ShoppingBasketProductId shoppingBasketProductId = new ShoppingBasketProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteShoppingBasketProductUseCaseDto.Input input = DeleteShoppingBasketProductUseCaseDto.Input.builder()
			.shoppingBasketProductId(shoppingBasketProductId)
			.userId(userId)
			.build();

		when(mockReadShoppingBasketProductPort.readOrThrow(any()))
			.thenReturn(TestDomainFactory.createShoppingBasketProduct(
				shoppingBasketProductId.getId(),
				userId.getId(),
				UUID.randomUUID(),
				BigDecimal.valueOf(1)
			));

		// when
		deleteShoppingBasketProductUseCase.execute(input);

		// then
		verify(mockReadShoppingBasketProductPort, times(1))
			.readOrThrow(shoppingBasketProductId);
		verify(mockDeleteShoppingBasketProductPort, times(1))
			.delete(shoppingBasketProductId);
	}

	@Test
	@DisplayName("장바구니의 주인이 아닌 사람이 삭제를 하면 예외를 던진다")
	void WhenDeleteShoppingBasketByNotOwnerThenThrowException() {
		// given
		ShoppingBasketProductId shoppingBasketProductId = new ShoppingBasketProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteShoppingBasketProductUseCaseDto.Input input = DeleteShoppingBasketProductUseCaseDto.Input.builder()
			.shoppingBasketProductId(shoppingBasketProductId)
			.userId(userId)
			.build();

		when(mockReadShoppingBasketProductPort.readOrThrow(any()))
			.thenReturn(TestDomainFactory.createShoppingBasketProduct(
				shoppingBasketProductId.getId(),
				UUID.randomUUID(),
				UUID.randomUUID(),
				BigDecimal.valueOf(1)
			));

		// when & then
		assertThatThrownBy(() -> deleteShoppingBasketProductUseCase.execute(input))
			.isInstanceOf(ShoppingBasketException.class);
	}

}
