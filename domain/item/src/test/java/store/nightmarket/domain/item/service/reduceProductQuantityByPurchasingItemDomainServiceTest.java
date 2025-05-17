package store.nightmarket.domain.item.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserBuyProductItem;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.nightmarket.domain.item.service.dto.reduceProductQuantityByPurchasingItemDomainServiceDto.Event;
import static store.nightmarket.domain.item.service.dto.reduceProductQuantityByPurchasingItemDomainServiceDto.Input;

class reduceProductQuantityByPurchasingItemDomainServiceTest {

    private reduceProductQuantityByPurchasingItemDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        service = new reduceProductQuantityByPurchasingItemDomainService();
    }

    @Test
    @DisplayName("productItem과 UserProduct의 id가 다르면 productItemException이 발생한다. ")
    void shouldThrowProductExceptionWhenProductIdIsDifferent() {
        // given
        ProductItem productItem = TestItemFactory.defaultProductItem();
        UserBuyProductItem userBuyProductItem = TestItemFactory.defaultUserProductItem();

        Input input = Input.builder()
                .productItem(productItem)
                .buyProductItem(userBuyProductItem)
                .build();

        // when & then
        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ProductItemException.class);
    }

    @Test
    @DisplayName("제품수량이 요청수량보다 많을때 요청 수량을 가진 UserBuyProductItem을 반환한다.")
    void shouldReduceProductQuantityWhenProductIsSufficient() {
        // given
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                5, 5, 5, 5, 5, 5
        );
        Input input = Input.builder()
                .productItem(testData.getProductItem())
                .buyProductItem(testData.getUserProductItem())
                .build();

        //when
        Event execute = service.execute(input);

        //then
        UserBuyProductItem buyProductItem = execute.getBuyProductItem();
        UserBuyProductItem requestProductItem = testData.getUserProductItem();

        softly.assertThat(buyProductItem.getItemId()).isEqualTo(
                requestProductItem.getItemId());
        softly.assertThat(buyProductItem.getBasicOption().getOptionGroupId()).isEqualTo(
                requestProductItem.getBasicOption().getOptionGroupId());
        softly.assertThat(buyProductItem.getAdditionalOption().getOptionId()).isEqualTo(
                requestProductItem.getAdditionalOption().getOptionId());
        softly.assertAll();
    }

    @Test
    @DisplayName("아이템 수량이 요청 수량보다 부족하면 ProductItemException을 던진다.")
    void shouldThrowProductItemExceptionWhenProductItemQuantityIsInsufficient() {
        // given
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                5, 5, 15, 5, 15, 5
        );
        Input input = Input.builder()
                .productItem(testData.getProductItem())
                .buyProductItem(testData.getUserProductItem())
                .build();

        // when & then
        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ProductItemException.class);
    }

}
