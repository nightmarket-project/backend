package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.CartProduct;
import store.nightmarket.itemweb.fixture.TestCartFactory;
import store.nightmarket.itemweb.service.dto.RemoveProductFromCartItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.RemoveProductFromCartItemWebDomainServiceDto.Input;

class RemoveProductFromCartItemWebDomainServiceTest {

    private RemoveProductFromCartItemWebDomainService service;
    private UUID cpuCartId;
    private UUID ramCartId;
    private UUID cpuId;
    private UUID ramId;

    @BeforeEach
    void setUp() {
        service = new RemoveProductFromCartItemWebDomainService();
        cpuCartId = UUID.randomUUID();
        ramCartId = UUID.randomUUID();
        cpuId = UUID.randomUUID();
        ramId = UUID.randomUUID();
    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 제거하면 해당 상품만 제거되고 나머지는 유지된다")
    void shouldRemoveProductFromCartWhenRemoved() {
        // given
        Cart cart = TestCartFactory.createCart();
        CartProduct cpuCartProduct = TestCartFactory.createCartProduct(
            cpuCartId,
            cpuId,
            "CPU",
            100,
            10000
        );
        CartProduct ramCartProduct = TestCartFactory.createCartProduct(
            ramCartId,
            ramId,
            "RAM",
            100,
            10000
        );

        cart.add(cpuCartProduct);
        cart.add(ramCartProduct);

        Input input = Input.builder()
            .cart(cart)
            .cartProduct(cpuCartProduct)
            .build();

        // when
        Event event = service.execute(input);

        // then
        assertThat(event.getCart().getShoppingBasket())
            .doesNotContain(cpuCartProduct);
        assertThat(event.getCart().getShoppingBasket())
            .contains(ramCartProduct);
    }

}
