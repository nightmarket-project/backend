package store.nightmarket.application.appitem.usecase.cart;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadShoppingBasketProductPort;
import store.nightmarket.application.appitem.out.SaveShoppingBasketProductPort;
import store.nightmarket.application.appitem.usecase.cart.dto.ModifyShoppingBasketQuantityUseCaseDto;
import store.nightmarket.domain.item.exception.ShoppingBasketException;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.ShoppingBasketProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.service.PutProductIntoShoppingBasketDomainService;
import store.nightmarket.domain.item.service.dto.PutProductIntoShoppingBasketDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Quantity;

class ModifyShoppingBasketQuantityUseCaseTest {

	private ModifyShoppingBasketQuantityUseCase modifyShoppingBasketQuantityUseCase;
	private ReadShoppingBasketProductPort mockReadShoppingBasketProductPort;
	private SaveShoppingBasketProductPort mockSaveShoppingBasketProductPort;
	private PutProductIntoShoppingBasketDomainService mockPutProductIntoShoppingBasketDomainService;

	@BeforeEach
	void setUp() {
		mockReadShoppingBasketProductPort = mock(ReadShoppingBasketProductPort.class);
		mockSaveShoppingBasketProductPort = mock(SaveShoppingBasketProductPort.class);
		mockPutProductIntoShoppingBasketDomainService = mock(PutProductIntoShoppingBasketDomainService.class);
		modifyShoppingBasketQuantityUseCase = new ModifyShoppingBasketQuantityUseCase(
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
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());

		ShoppingBasketProduct shoppingBasketProduct = TestDomainFactory.createShoppingBasketProduct(
			shoppingBasketProductId.getId(),
			userId.getId(),
			productVariantId.getId(),
			BigInteger.ONE
		);

		Quantity modifiedQuantity = new Quantity(BigInteger.TEN);

		ShoppingBasketProduct modifiedShoppingBasketProduct = TestDomainFactory.createShoppingBasketProduct(
			shoppingBasketProductId.getId(),
			userId.getId(),
			productVariantId.getId(),
			modifiedQuantity.value()
		);

		ModifyShoppingBasketQuantityUseCaseDto.Input input = ModifyShoppingBasketQuantityUseCaseDto.Input.builder()
			.shoppingBasketProductId(shoppingBasketProductId)
			.userId(userId)
			.quantity(modifiedQuantity)
			.build();

		PutProductIntoShoppingBasketDomainServiceDto.Event event = PutProductIntoShoppingBasketDomainServiceDto.Event.builder()
			.shoppingBasketProduct(modifiedShoppingBasketProduct)
			.build();

		when(mockReadShoppingBasketProductPort.readOrThrow(shoppingBasketProductId))
			.thenReturn(shoppingBasketProduct);

		when(mockPutProductIntoShoppingBasketDomainService.execute(
			any(PutProductIntoShoppingBasketDomainServiceDto.Input.class)))
			.thenReturn(event);

		// when
		modifyShoppingBasketQuantityUseCase.execute(input);

		// then
		verify(mockReadShoppingBasketProductPort, times(1))
			.readOrThrow(shoppingBasketProductId);

		verify(mockPutProductIntoShoppingBasketDomainService, times(1))
			.execute(any());

		ArgumentCaptor<ShoppingBasketProduct> argumentCaptor = ArgumentCaptor.forClass(ShoppingBasketProduct.class);

		verify(mockSaveShoppingBasketProductPort, times(1))
			.save(argumentCaptor.capture());

		ShoppingBasketProduct savedShoppingBasketProduct = argumentCaptor.getValue();

		Assertions.assertThat(modifiedQuantity).isEqualTo(savedShoppingBasketProduct.getQuantity());
	}

	@Test
	@DisplayName("장바구니상품의 주인이 아닌 사람이 수정을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ShoppingBasketProductId shoppingBasketProductId = new ShoppingBasketProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());
		Quantity quantity = new Quantity(BigInteger.TEN);
		UUID productVariantId = UUID.randomUUID();

		ShoppingBasketProduct shoppingBasketProduct = TestDomainFactory.createShoppingBasketProduct(
			shoppingBasketProductId.getId(),
			UUID.randomUUID(),
			productVariantId,
			BigInteger.ONE
		);

		ModifyShoppingBasketQuantityUseCaseDto.Input input = ModifyShoppingBasketQuantityUseCaseDto.Input.builder()
			.shoppingBasketProductId(shoppingBasketProductId)
			.userId(userId)
			.quantity(quantity)
			.build();

		when(mockReadShoppingBasketProductPort.readOrThrow(shoppingBasketProductId))
			.thenReturn(shoppingBasketProduct);

		// when
		// then
		assertThatThrownBy(() -> modifyShoppingBasketQuantityUseCase.execute(input))
			.isInstanceOf(ShoppingBasketException.class);

		verify(mockReadShoppingBasketProductPort, times(1))
			.readOrThrow(shoppingBasketProductId);

		verify(mockPutProductIntoShoppingBasketDomainService, never())
			.execute(any());

		verify(mockSaveShoppingBasketProductPort, never())
			.save(any());
	}

}
