package store.nightmarket.itemweb.service;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.ShoppingBaseketProduct;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.itemweb.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Input;

class AddProductToShoppingBasketItemWebDomainServiceTest {

    private AddProductToShoppingBasketItemWebDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new AddProductToShoppingBasketItemWebDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("장바구니에 상품을 추가하면 장바구니에 해당 상품이 포함된다")
    void shouldContainProductInCartWhenProductIsAdded() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct shoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        Input input = Input.builder()
            .shoppingBasket(shoppingBasket)
            .shoppingBaseketProduct(shoppingBaseketProduct)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event.getShoppingBasket().getShoppingBasket())
            .hasSize(1);
        softly.assertThat(event.getShoppingBasket().getShoppingBasket())
            .contains(shoppingBaseketProduct);
        softly.assertAll();
    }

}
