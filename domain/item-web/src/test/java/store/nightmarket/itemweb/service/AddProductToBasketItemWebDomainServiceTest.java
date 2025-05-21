package store.nightmarket.itemweb.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserBuyProductItem;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestItemFactory;
import store.nightmarket.itemweb.model.ShoppingBasket;
import store.nightmarket.itemweb.valueobject.ShoppingBasketId;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.nightmarket.itemweb.service.dto.AddProductToBasketItemWebDomainServiceDto.Event;
import static store.nightmarket.itemweb.service.dto.AddProductToBasketItemWebDomainServiceDto.Input;

class AddProductToBasketItemWebDomainServiceTest {

    private AddProductToBasketItemWebDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new AddProductToBasketItemWebDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("구매 할 제품과 구매 요청 제품의 id가 다르면 ItemWebException 예외가 발생한다.")
    void shouldThrowItemWebExceptionWhenProductIdIsDifferent() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                UUID.randomUUID(), 10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                UUID.randomUUID(), 5, 5, 5, 5, 5, 5);
        ShoppingBasket shoppingBasket = newShoppingBasket();

        Input input = Input.builder()
                .productItem(testProductItem)
                .userBuyProductItem(testUserBuyProductItem)
                .shoppingBasket(shoppingBasket)
                .build();

        // when & then
        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("제품의 세부 옵션 수량이 충분할때 세부 옵션 수량에 맞는 아이템을 장바구니에 담는다.")
    void shouldAddProductToBasketWhenProductIsSufficient() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                5, 5, 5, 5, 5, 5);

        ShoppingBasket shoppingBasket = newShoppingBasket();
        Input input = Input.builder()
                .productItem(testProductItem)
                .userBuyProductItem(testUserBuyProductItem)
                .shoppingBasket(shoppingBasket)
                .build();

        // when
        Event event = service.execute(input);

        // then
        List<UserBuyProductItem> userBuyProductItems = event.getShoppingBasket().getUserBuyProductItems();
        softly.assertThat(userBuyProductItems)
                .hasSize(1);
        softly.assertThat(userBuyProductItems.getFirst().getItemId())
                .isEqualTo(testUserBuyProductItem.getItemId());
        softly.assertAll();
    }

    @Test
    @DisplayName("아이템 세부 수량이 요청 수량보다 부족하면 ItemWebException을 발생한다.")
    void shouldThrowItemWebExceptionWhenProductItemQuantityIsInsufficient() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                5, 5, 15, 5, 15, 5);


        ShoppingBasket shoppingBasket = newShoppingBasket();
        Input input = Input.builder()
                .productItem(testProductItem)
                .userBuyProductItem(testUserBuyProductItem)
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
