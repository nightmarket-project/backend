package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.dto.ReadShoppingBasketUseCaseDto;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.UserId;

class ReadShoppingBasketUseCaseTest {

	private ReadShoppingBasketProductPort mockReadShoppingBasketProductPort;
	private ReadShoppingBasketUseCase readShoppingBasketUseCase;

	@BeforeEach
	void setUp() {
		mockReadShoppingBasketProductPort = mock(ReadShoppingBasketProductPort.class);
		readShoppingBasketUseCase = new ReadShoppingBasketUseCase(mockReadShoppingBasketProductPort);
	}

	@Test
	@DisplayName("장바구니 목록 조회")
	void readShoppingBasket() {
		// given
		UserId userId = new UserId(UUID.randomUUID());

		ReadShoppingBasketUseCaseDto.Input input = ReadShoppingBasketUseCaseDto.Input.builder()
			.userId(userId)
			.build();

		List<ShoppingBasketProduct> shoppingBasketProductList = List.of(
			TestDomainFactory.createShoppingBasketProduct(
				UUID.randomUUID(),
				userId.getId(),
				UUID.randomUUID(),
				BigDecimal.TEN
			),
			TestDomainFactory.createShoppingBasketProduct(
				UUID.randomUUID(),
				userId.getId(),
				UUID.randomUUID(),
				BigDecimal.ONE
			)
		);

		when(mockReadShoppingBasketProductPort.readListByUserId(userId))
			.thenReturn(shoppingBasketProductList);
		// when
		ReadShoppingBasketUseCaseDto.Output output = readShoppingBasketUseCase.execute(input);

		// then
		assertThat(output).isNotNull();
		assertThat(output.shoppingBasketProductList())
			.isNotEmpty();
		assertThat(output.shoppingBasketProductList())
			.hasSize(2);
		assertThat(output.shoppingBasketProductList())
			.isEqualTo(shoppingBasketProductList);
		verify(mockReadShoppingBasketProductPort, times(1))
			.readListByUserId(userId);
	}

}
