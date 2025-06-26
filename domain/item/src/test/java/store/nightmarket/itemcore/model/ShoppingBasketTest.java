package store.nightmarket.itemcore.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ItemCoreException;
import store.nightmarket.itemcore.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemcore.valueobject.Quantity;

class ShoppingBasketTest {

    @Test
    @DisplayName("장바구니에 상품을 추가하면 해당 상품이 장바구니에 담긴다")
    void shouldAddProductToCartWhenCartProductIsAdded() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct shoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        // when
        shoppingBasket.add(shoppingBaseketProduct);

        // then
        assertThat(shoppingBasket.getShoppingBasket())
            .hasSize(1);
        assertThat(shoppingBasket.getShoppingBasket())
            .containsExactly(shoppingBaseketProduct);
    }

    @Test
    @DisplayName("장바구니에 해당 상품이 존재할때 해당 상품이 장바구니에 제거한다")
    void shouldRemoveProductFromCartWhenCartProductIsRemoved() {
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

        shoppingBasket.add(cpuShoppingBaseketProduct);
        shoppingBasket.add(ramShoppingBaseketProduct);

        // when
        shoppingBasket.remove(ramShoppingBaseketProduct);

        // then
        assertThat(shoppingBasket.getShoppingBasket())
            .contains(cpuShoppingBaseketProduct);
        assertThat(shoppingBasket.getShoppingBasket())
            .doesNotContain(ramShoppingBaseketProduct);
    }

    @Test
    @DisplayName("장바구니에 해당 상품이 존재하지 않을때 예외를 던진다.")
    void shouldThrowExceptionWhenCartProductIsNotExist() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        shoppingBasket.add(cpuShoppingBaseketProduct);

        // when
        // then
        ShoppingBaseketProduct ramShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "RAM",
            100,
            10000
        );

        assertThatThrownBy(() -> shoppingBasket.remove(ramShoppingBaseketProduct))
            .isInstanceOf(ItemCoreException.class);
    }

    @Test
    @DisplayName("장바구니에서 상품 수량을 수정하면 변경된 수량이 반영된다")
    void shouldChangeCartProductQuantityWhenUpdated() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        shoppingBasket.add(cpuShoppingBaseketProduct);

        // when
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));

        shoppingBasket.changeProductQuantity(cpuShoppingBaseketProduct, quantity);

        // then
        assertThat(shoppingBasket.getShoppingBasket().getFirst().getQuantity())
            .isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 없는 상품의 수량을 수정하려 할 때 예외를 던진다")
    void shouldThrowExceptionWhenCartISNotExistProduct() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        shoppingBasket.add(cpuShoppingBaseketProduct);

        // when
        // then
        Quantity quantity = new Quantity(BigDecimal.valueOf(1));
        ShoppingBaseketProduct ramShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "RAM",
            100,
            10000
        );

        assertThatThrownBy(() -> shoppingBasket.changeProductQuantity(ramShoppingBaseketProduct, quantity))
            .isInstanceOf(ItemCoreException.class);
    }

    @Test
    @DisplayName("장바구니에서 상품 수량을 0이하 값으로 수정하면 예외를 던진다.")
    void shouldThrowExceptionWhenChangingProductQuantityToZeroOrLess() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        shoppingBasket.add(cpuShoppingBaseketProduct);

        // when
        // then
        Quantity quantity = new Quantity(BigDecimal.valueOf(0));
        assertThatThrownBy(() -> shoppingBasket.changeProductQuantity(cpuShoppingBaseketProduct, quantity))
            .isInstanceOf(ItemCoreException.class);
    }

}
