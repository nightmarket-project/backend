package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBaseketProduct;
import store.nightmarket.itemweb.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.AddProductToShoppingBasketItemWebDomainServiceDto.Input;

class AddProductToShoppingBasketItemWebDomainServiceTest {

    private AddProductToShoppingBasketItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new AddProductToShoppingBasketItemWebDomainService();
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
        assertThat(event.getShoppingBasket().getShoppingBasket())
            .hasSize(1);
        assertThat(event.getShoppingBasket().getShoppingBasket())
            .contains(shoppingBaseketProduct);
    }

}
