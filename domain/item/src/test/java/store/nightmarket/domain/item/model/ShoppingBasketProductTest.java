package store.nightmarket.domain.item.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.fixture.TestShoppingBasketFactory;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

class ShoppingBasketProductTest {

    @Test
    @DisplayName("양의 수량이 주어졌을때 상품의 수량을 바꾼다.")
    void shouldChangeQuantityWhenGivenPositiveQuantity() {
        // given
        UUID userId = UUID.randomUUID();
        ShoppingBasketProduct cpu = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            userId,
            "CPU",
            1000,
            100
        );
        Quantity oneQuantity = new Quantity(BigDecimal.ONE);

        // when
        cpu.changeQuantity(
            new UserId(userId),
            oneQuantity
        );

        // then
        Quantity expectedQuantity = new Quantity(BigDecimal.ONE);

        assertThat(cpu.getQuantity()).isEqualTo(expectedQuantity);
    }

    @Test
    @DisplayName("0 이하의 수량으로 changeQuantity를 실행했을 때 ProductException을 던져야 한다")
    void shouldThrowProductExceptionWhenChangeQuantityWithZeroOrNegativeQuantity() {
        // given
        UUID userId = UUID.randomUUID();
        ShoppingBasketProduct cpu = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            userId,
            "CPU",
            1000,
            100
        );
        Quantity zeroQuantity = new Quantity(BigDecimal.ZERO);

        // when
        // then
        assertThatThrownBy(
            () -> cpu.changeQuantity(
                new UserId(userId),
                zeroQuantity
            )
        ).isInstanceOf(ProductException.class);
    }

    @Test
    @DisplayName("유저 아이디가 다를때 예외를 던진다.")
    void shouldThrowExceptionWhenUserIdIsDifferent() {
        // given
        UUID userId = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        ShoppingBasketProduct cpu = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            userId,
            "CPU",
            1000,
            100
        );
        Quantity quantity = new Quantity(BigDecimal.TEN);

        // when
        // then
        assertThatThrownBy(
            () -> cpu.changeQuantity(
                new UserId(userId2),
                quantity
            )
        ).isInstanceOf(ProductException.class);
    }

    @Test
    @DisplayName("상품의 수량과 단가가 주어졌을 때 총 가격을 올바르게 계산해야 한다")
    void shouldCalculateTotalPriceWhenGivenQuantityAndUnitPrice() {
        // given
        ShoppingBasketProduct cpu = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            1000,
            100
        );

        // when
        Price totalPrice = cpu.getTotalPrice();

        // then
        Price expectedPrice = new Price(BigDecimal.valueOf(100000));

        assertThat(totalPrice).isEqualTo(expectedPrice);
    }

}
