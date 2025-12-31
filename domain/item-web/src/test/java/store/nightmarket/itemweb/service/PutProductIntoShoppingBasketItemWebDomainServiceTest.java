package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemweb.service.dto.PutProductIntoShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.PutProductIntoShoppingBasketItemWebDomainServiceDto.Input;

class PutProductIntoShoppingBasketItemWebDomainServiceTest {

	private PutProductIntoShoppingBasketItemWebDomainService service;
	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		service = new PutProductIntoShoppingBasketItemWebDomainService();
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("상품 수량 변경 요청을 처리하면 장바구니에 수량이 반영된다")
	void shouldUpdateProductQuantityInCartWhenServiceIsExecuted() {
		// given
		UUID userId = UUID.randomUUID();
		ShoppingBasketProduct shoppingBasketProduct = TestShoppingBasketFactory.createCartProduct(
			UUID.randomUUID(),
			UUID.randomUUID(),
			userId,
			"CPU",
			100,
			10000
		);
		Quantity changeQuantity = new Quantity(BigInteger.ONE);

		Input input = Input.builder()
			.shoppingBasketProduct(shoppingBasketProduct)
			.quantity(changeQuantity)
			.userId(new UserId(userId))
			.build();

		// when
		Event event = service.execute(input);

		// then
		Quantity expectedQuantity = new Quantity(BigInteger.ONE);

		softly.assertThat(event)
			.isNotNull();
		softly.assertThat(event.getShoppingBasketProduct().getQuantity())
			.isEqualTo(expectedQuantity);
		softly.assertAll();
	}

	@Test
	@DisplayName("상품 수량을 0 이하로 변경 요청하면 예외가 발생한다")
	void shouldThrowExceptionWhenUpdatingCartProductQuantityToZeroOrLess() {
		// given
		UUID userId = UUID.randomUUID();
		ShoppingBasketProduct shoppingBasketProduct = TestShoppingBasketFactory.createCartProduct(
			UUID.randomUUID(),
			UUID.randomUUID(),
			userId,
			"CPU",
			100,
			10000
		);
		Quantity changeQuantity = new Quantity(BigInteger.ZERO);

		Input input = Input.builder()
			.shoppingBasketProduct(shoppingBasketProduct)
			.quantity(changeQuantity)
			.userId(new UserId(userId))
			.build();

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(ProductException.class);
	}

	@Test
	@DisplayName("유저 아이디가 다를때 예외를 던진다.")
	void shouldThrowExceptionWhenUserIdIsDifferent() {
		// given
		UUID userId = UUID.randomUUID();
		UserId otherUserId = new UserId(UUID.randomUUID());
		ShoppingBasketProduct cpu = TestShoppingBasketFactory.createCartProduct(
			UUID.randomUUID(),
			UUID.randomUUID(),
			userId,
			"CPU",
			1000,
			100
		);
		Quantity quantity = new Quantity(BigInteger.TEN);

		Input input = Input.builder()
			.shoppingBasketProduct(cpu)
			.quantity(quantity)
			.userId(otherUserId)
			.build();

		// when
		// then
		assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(ProductException.class);
	}

}
