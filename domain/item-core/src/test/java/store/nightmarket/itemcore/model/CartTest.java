package store.nightmarket.itemcore.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.fixture.TestCartFactory;
import store.nightmarket.itemcore.valueobject.Quantity;

class CartTest {

    @Test
    @DisplayName("장바구니에 상품을 추가하면 해당 상품이 장바구니에 담긴다")
    void shouldAddProductToCartWhenCartProductIsAdded() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        // when
        cart.add(cartProduct);

        // then
        assertThat(cart.getShoppingBasket())
            .hasSize(1);
        assertThat(cart.getShoppingBasket())
            .containsExactly(cartProduct);
    }

    @Test
    @DisplayName("장바구니에 해당 상품이 존재할때 해당 상품이 장바구니에 제거한다")
    void shouldRemoveProductFromCartWhenCartProductIsRemoved() {
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

        cart.add(cpuCartProduct);
        cart.add(ramCartProduct);

        // when
        cart.remove(ramCartProduct);

        // then
        assertThat(cart.getShoppingBasket())
            .contains(cpuCartProduct);
        assertThat(cart.getShoppingBasket())
            .doesNotContain(ramCartProduct);
    }

    @Test
    @DisplayName("장바구니에 해당 상품이 존재하지 않을때 예외를 던진다.")
    void shouldThrowExceptionWhenCartProductIsNotExist() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        cart.add(cpuCartProduct);

        // when
        // then
        CartProduct ramCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "RAM",
            100,
            10000
        );

        assertThatThrownBy(() -> cart.remove(ramCartProduct))
            .isInstanceOf(ItemCoreException.class);
    }

    @Test
    @DisplayName("장바구니에서 상품 수량을 수정하면 변경된 수량이 반영된다")
    void shouldChangeCartProductQuantityWhenUpdated() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        cart.add(cpuCartProduct);

        // when
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));

        cart.changeProductQuantity(cpuCartProduct, quantity);

        // then
        assertThat(cart.getShoppingBasket().getFirst().getQuantity())
            .isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 없는 상품의 수량을 수정하려 할 때 예외를 던진다")
    void shouldThrowExceptionWhenCartISNotExistProduct() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        cart.add(cpuCartProduct);

        // when
        // then
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));
        CartProduct ramCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "RAM",
            100,
            10000
        );

        assertThatThrownBy(() -> cart.changeProductQuantity(ramCartProduct, quantity))
            .isInstanceOf(ItemCoreException.class);
    }

    @Test
    @DisplayName("장바구니에서 상품 수량을 0이하 값으로 수정하면 예외를 던진다.")
    void shouldThrowExceptionWhenChangingProductQuantityToZeroOrLess() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        cart.add(cpuCartProduct);

        // when
        // then
        Quantity quantity = new Quantity(BigDecimal.valueOf(0));
        assertThatThrownBy(() -> cart.changeProductQuantity(cpuCartProduct, quantity))
            .isInstanceOf(ItemCoreException.class);
    }

}
