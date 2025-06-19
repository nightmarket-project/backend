package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemweb.fixture.TestCartFactory;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToCartItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeProductQuantityToCartItemWebDomainServiceDto.Input;

class ChangeProductQuantityToCartItemWebDomainServiceTest {

    private ChangeProductQuantityToCartItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new ChangeProductQuantityToCartItemWebDomainService();
    }

    @Test
    @DisplayName("상품 수량 변경 요청을 처리하면 장바구니에 수량이 반영된다")
    void shouldUpdateProductQuantityInCartWhenServiceIsExecuted() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));

        cart.add(cpuCartProduct);

        Input input = Input.builder()
            .cart(cart)
            .cartProduct(cpuCartProduct)
            .quantity(quantity)
            .build();

        // when
        Event event = service.execute(input);

        // then
        assertThat(event.getCart().getShoppingBasket().getFirst().getQuantity())
            .isEqualTo(new Quantity(BigDecimal.valueOf(1)));
    }

    @Test
    @DisplayName("장바구니에 없는 상품의 수량 변경을 요청하면 예외가 발생한다")
    void shouldThrowExceptionWhenChangingQuantityOfProductNotInCart()  {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        CartProduct ramCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "RAM",
            100,
            10000
        );
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));

        cart.add(cpuCartProduct);

        Input input = Input.builder()
            .cart(cart)
            .cartProduct(ramCartProduct)
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
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        Quantity quantity = new Quantity(BigDecimal.valueOf(0));

        cart.add(cpuCartProduct);

        Input input = Input.builder()
            .cart(cart)
            .cartProduct(cpuCartProduct)
            .quantity(quantity)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ItemCoreException.class);
    }

}
