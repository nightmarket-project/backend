package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.ShoppingBasket;
import store.nightmarket.domain.item.model.ShoppingBaseketProduct;
import store.nightmarket.itemweb.fixture.TestShoppingBasketFactory;
import store.nightmarket.itemweb.service.dto.RemoveProductFromShoppingBasketItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.RemoveProductFromShoppingBasketItemWebDomainServiceDto.Input;

class RemoveProductFromShoppingBasketItemWebDomainServiceTest {

    private RemoveProductFromShoppingBasketItemWebDomainService service;
    private SoftAssertions softly;
    private UUID cpuCartId;
    private UUID ramCartId;
    private UUID cpuId;
    private UUID ramId;

    @BeforeEach
    void setUp() {
        service = new RemoveProductFromShoppingBasketItemWebDomainService();
        softly = new SoftAssertions();
        cpuCartId = UUID.randomUUID();
        ramCartId = UUID.randomUUID();
        cpuId = UUID.randomUUID();
        ramId = UUID.randomUUID();
    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 제거하면 해당 상품만 제거되고 나머지는 유지된다")
    void shouldRemoveProductFromCartWhenRemoved() {
        // given
        ShoppingBasket shoppingBasket = TestShoppingBasketFactory.createCart();
        ShoppingBaseketProduct cpuShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            cpuCartId,
            cpuId,
            "CPU",
            100,
            10000
        );
        ShoppingBaseketProduct ramShoppingBaseketProduct = TestShoppingBasketFactory.createCartProduct(
            ramCartId,
            ramId,
            "RAM",
            100,
            10000
        );

        shoppingBasket.add(cpuShoppingBaseketProduct);
        shoppingBasket.add(ramShoppingBaseketProduct);

        Input input = Input.builder()
            .shoppingBasket(shoppingBasket)
            .shoppingBaseketProduct(cpuShoppingBaseketProduct)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event.getShoppingBasket().getShoppingBasket())
            .doesNotContain(cpuShoppingBaseketProduct);
        softly.assertThat(event.getShoppingBasket().getShoppingBasket())
            .contains(ramShoppingBaseketProduct);
        softly.assertAll();
    }

}
