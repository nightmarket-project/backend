package store.nightmarket.itemweb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestItemFactory;
import store.nightmarket.itemweb.model.ShoppingBasket;
import store.nightmarket.itemweb.valueobject.ShoppingBasketId;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.nightmarket.itemweb.service.dto.removeProductFromBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.removeProductFromBasketItemWebDomainServiceDto.Input;

class removeProductFromBasketItemWebDomainServiceTest {

    private removeProductFromBasketItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new removeProductFromBasketItemWebDomainService();
    }

    @Test
    @DisplayName("장바구니에 삭제 제품과 동일한 제품이 있을때 장바구니에서 제품을 삭제한다.")
    void ShouldRemoveProductFromBasketWhenBasketHaveProduct() {
        // given
        ShoppingBasket shoppingBasket = newShoppingBasket();
        UserBuyProductItem userBuyProductItem = TestItemFactory.defaultUserProductItem();
        shoppingBasket.addProductToBasket(userBuyProductItem);
        Input input = Input.builder()
                .userBuyProductItem(userBuyProductItem)
                .shoppingBasket(shoppingBasket)
                .build();

        // when
        Event event = service.execute(input);

        // then
        List<UserBuyProductItem> userBuyProductItems = event.getShoppingBasket().getUserBuyProductItems();
        assertThat(userBuyProductItems).isEmpty();
    }

    @Test
    @DisplayName("장바구니에 삭제 제품이 없다면 ItemWebException이 발생한다.")
    void shouldThrowItemWebExceptionWhenBasketDontHaveProduct() {
        // given
        ShoppingBasket shoppingBasket = newShoppingBasket();
        UserBuyProductItem userBuyProductItem = TestItemFactory.defaultUserProductItem();
        Input input = Input.builder()
                .userBuyProductItem(userBuyProductItem)
                .shoppingBasket(shoppingBasket)
                .build();

        // when & then
        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ItemWebException.class);
    }

    private ShoppingBasket newShoppingBasket() {
        return ShoppingBasket.newInstance(
                new ShoppingBasketId(UUID.randomUUID()),
                new UserId(UUID.randomUUID())
        );
    }

}
