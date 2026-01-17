package store.nightmarket.application.appitem.usecase.cart;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.cart.dto.ModifyShoppingBasketQuantityUseCaseDto;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.service.PutProductIntoShoppingBasketDomainService;
import store.nightmarket.domain.item.service.dto.PutProductIntoShoppingBasketDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Quantity;

class ModifyShoppingBasketQuantityUseCaseTest {

	private ReadShoppingBasketProductPort mockReadShoppingBasketProductPort;
	private SaveShoppingBasketProductPort mockSaveShoppingBasketProductPort;
	private PutProductIntoShoppingBasketDomainService mockPutProductIntoShoppingBasketDomainService;
	private ModifyShoppingBasketQuantityUseCase modifyShoppingBasketQuantity;

	@BeforeEach
	void setUp() {
		mockReadShoppingBasketProductPort = mock(ReadShoppingBasketProductPort.class);
		mockSaveShoppingBasketProductPort = mock(SaveShoppingBasketProductPort.class);
		mockPutProductIntoShoppingBasketDomainService =
			mock(PutProductIntoShoppingBasketDomainService.class);
		modifyShoppingBasketQuantity = new ModifyShoppingBasketQuantityUseCase(
			mockReadShoppingBasketProductPort,
			mockSaveShoppingBasketProductPort,
			mockPutProductIntoShoppingBasketDomainService
		);
	}

	@Test
	@DisplayName("장바구니 상품 수량 변경")
	void modifyShoppingBasketProductQuantity() {
		// given
		ShoppingBasketProductId shoppingBasketProductId = new ShoppingBasketProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());
		Quantity quantity = new Quantity(BigInteger.TEN);
		UUID productVariantId = UUID.randomUUID();

		ShoppingBasketProduct shoppingBasketProduct = TestDomainFactory.createShoppingBasketProduct(
			shoppingBasketProductId.getId(),
			userId.getId(),
			productVariantId,
			BigInteger.ONE
		);

		ModifyShoppingBasketQuantityUseCaseDto.Input input = ModifyShoppingBasketQuantityUseCaseDto.Input.builder()
			.shoppingBasketProductId(shoppingBasketProductId)
			.userId(userId)
			.quantity(quantity)
			.build();

		PutProductIntoShoppingBasketDomainServiceDto.Event serviceEvent =
			PutProductIntoShoppingBasketDomainServiceDto.Event.builder()
				.shoppingBasketProduct(
					TestDomainFactory.createShoppingBasketProduct(
						shoppingBasketProductId.getId(),
						userId.getId(),
						productVariantId,
						BigInteger.TEN
					))
				.build();

		when(mockReadShoppingBasketProductPort.readOrThrow(any(ShoppingBasketProductId.class)))
			.thenReturn(shoppingBasketProduct);
		when(mockPutProductIntoShoppingBasketDomainService.execute(
			any(PutProductIntoShoppingBasketDomainServiceDto.Input.class)))
			.thenReturn(serviceEvent);

		// when
		modifyShoppingBasketQuantity.execute(input);

		// then
		verify(mockReadShoppingBasketProductPort, times(1))
			.readOrThrow(shoppingBasketProductId);
		verify(mockPutProductIntoShoppingBasketDomainService, times(1))
			.execute(any());
		verify(mockSaveShoppingBasketProductPort, times(1))
			.save(any());
	}

}
