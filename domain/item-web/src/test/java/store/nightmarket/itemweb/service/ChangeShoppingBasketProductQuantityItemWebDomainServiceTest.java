package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ShoppingBasketProduct;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.itemweb.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemweb.service.dto.ChangeShoppingBasketProductQuantityItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.ChangeShoppingBasketProductQuantityItemWebDomainServiceDto.Input;

class ChangeShoppingBasketProductQuantityItemWebDomainServiceTest {

    private ChangeShoppingBasketProductQuantityItemWebDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new ChangeShoppingBasketProductQuantityItemWebDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("상품 수량 변경 요청을 처리하면 장바구니에 수량이 반영된다")
    void shouldUpdateProductQuantityInCartWhenServiceIsExecuted() {
        // given
        ShoppingBasketProduct shoppingBasketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        Quantity changeQuantity = new Quantity(BigDecimal.ONE);

        Input input = Input.builder()
            .shoppingBasketProduct(shoppingBasketProduct)
            .quantity(changeQuantity)
            .build();

        // when
        Event event = service.execute(input);

        // then
        Quantity expectedQuantity = new Quantity(BigDecimal.ONE);

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
        ShoppingBasketProduct shoppingBasketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );
        Quantity changeQuantity = new Quantity(BigDecimal.ZERO);

        Input input = Input.builder()
            .shoppingBasketProduct(shoppingBasketProduct)
            .quantity(changeQuantity)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ProductException.class);
    }

}
