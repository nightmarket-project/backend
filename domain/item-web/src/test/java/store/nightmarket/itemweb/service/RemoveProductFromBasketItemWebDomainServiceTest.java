package store.nightmarket.itemweb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.UserBuyItemGroup;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestItemFactory;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.valueobject.CartId;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.nightmarket.itemweb.service.dto.RemoveProductFromBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.RemoveProductFromBasketItemWebDomainServiceDto.Input;

class RemoveProductFromBasketItemWebDomainServiceTest {

    private RemoveProductFromBasketItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new RemoveProductFromBasketItemWebDomainService();
    }

    @Test
    @DisplayName("장바구니에 삭제 제품과 동일한 제품이 있을때 장바구니에서 제품을 삭제한다.")
    void ShouldRemoveProductFromBasketWhenBasketHaveProduct() {
        // given
        TestItemFactory factory = new TestItemFactory();
        UserBuyItemGroup testUserBuyItemGroup = factory.createTestUserBuyProductItem(
                5, 5, 5, 5, 5, 5);
        Cart cart = newShoppingBasket();

        cart.addProductToBasket(testUserBuyItemGroup);
        Input input = Input.builder()
                .userBuyItem(testUserBuyItemGroup)
                .cart(cart)
                .build();

        // when
        Event event = service.execute(input);

        // then
        List<UserBuyItemGroup> userBuyItemGroups = event.getCart().getUserBuyItemGroups();
        assertThat(userBuyItemGroups)
                .isEmpty();
    }

    @Test
    @DisplayName("장바구니에 삭제 제품이 없다면 ItemWebException이 발생한다.")
    void shouldThrowItemWebExceptionWhenBasketDontHaveProduct() {
        // given
        Cart cart = newShoppingBasket();
        TestItemFactory factory = new TestItemFactory();
        UserBuyItemGroup testUserBuyItemGroup = factory.createTestUserBuyProductItem(
                5, 5, 5, 5, 5, 5);
        Input input = Input.builder()
                .userBuyItem(testUserBuyItemGroup)
                .cart(cart)
                .build();

        // when & then
        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ItemWebException.class);
    }

    private Cart newShoppingBasket() {
        return Cart.newInstance(
                new CartId(UUID.randomUUID()),
                new UserId(UUID.randomUUID())
        );
    }

}
