package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.model.ShoppingBasket;
import store.nightmarket.itemcore.model.ShoppingBaseketProduct;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemweb.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToShoppingBasketItemWebDomainServiceDto.Input;

class ChangeProductQuantityToShoppingBasketItemWebDomainServiceTest {

    private ChangeProductQuantityToShoppingBasketItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new ChangeProductQuantityToShoppingBasketItemWebDomainService();
    }

    @Test
    @DisplayName("상품 수량 변경 요청을 처리하면 장바구니에 수량이 반영된다")
    void shouldUpdateProductQuantityInCartWhenServiceIsExecuted() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));

        shoppingBasket.add(cpuShoppingBaseketProduct);

        Input input = Input.builder()
            .shoppingBasket(shoppingBasket)
            .shoppingBaseketProduct(cpuShoppingBaseketProduct)
            .quantity(quantity)
            .build();

        // when
        Event event = service.execute(input);

        // then
        assertThat(event.getShoppingBasket().getShoppingBasket().getFirst().getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(1)));
    }

    @Test
    @DisplayName("장바구니에 없는 상품의 수량 변경을 요청하면 예외가 발생한다")
    void shouldThrowExceptionWhenChangingQuantityOfProductNotInCart()  {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        ShoppingBaseketProduct ramShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "RAM",
            100,
            10000
        );
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));

        shoppingBasket.add(cpuShoppingBaseketProduct);

        Input input = Input.builder()
            .shoppingBasket(shoppingBasket)
            .shoppingBaseketProduct(ramShoppingBaseketProduct)
            .quantity(quantity)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ItemCoreException.class);
    }

    @Test
    @DisplayName("상품 수량을 0 이하로 변경 요청하면 예외가 발생한다")
    void shouldThrowExceptionWhenUpdatingCartProductQuantityToZeroOrLess() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        Quantity quantity = new Quantity(BigDecimal.valueOf(0));

        shoppingBasket.add(cpuShoppingBaseketProduct);

        Input input = Input.builder()
            .shoppingBasket(shoppingBasket)
            .shoppingBaseketProduct(cpuShoppingBaseketProduct)
            .quantity(quantity)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ItemCoreException.class);
    }

}
