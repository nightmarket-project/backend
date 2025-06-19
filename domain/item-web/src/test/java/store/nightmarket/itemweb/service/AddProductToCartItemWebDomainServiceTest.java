package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemweb.fixture.TestCartFactory;
import store.nightmarket.itemweb.service.dto.AddProductToCartItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.AddProductToCartItemWebDomainServiceDto.Input;

class AddProductToCartItemWebDomainServiceTest {

    private AddProductToCartItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new AddProductToCartItemWebDomainService();
    }

    @Test
    @DisplayName("장바구니에 상품을 추가하면 장바구니에 해당 상품이 포함된다")
    void shouldContainProductInCartWhenProductIsAdded() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cartProduct = TestCartFactory.createCartProduct(
            UUID.randomUUID(),
            UUID.randomUUID(),
            "CPU",
            100,
            10000
        );

        Input input = Input.builder()
            .cart(cart)
            .cartProduct(cartProduct)
            .build();

        // when
        Event event = service.execute(input);

        // then
        assertThat(event.getCart().getShoppingBasket())
            .hasSize(1);
        assertThat(event.getCart().getShoppingBasket())
            .contains(cartProduct);
    }

}
