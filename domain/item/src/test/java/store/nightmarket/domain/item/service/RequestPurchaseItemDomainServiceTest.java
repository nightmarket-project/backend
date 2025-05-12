package store.nightmarket.domain.item.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserProductItem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.nightmarket.domain.item.service.dto.RequestPurchaseItemDomainServiceDto.Event;
import static store.nightmarket.domain.item.service.dto.RequestPurchaseItemDomainServiceDto.Input;


class RequestPurchaseItemDomainServiceTest {

    private final RequestPurchaseItemDomainService service = new RequestPurchaseItemDomainService();
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("정상적인 구매 요청 시 Event를 반환한다.")
    void shouldReturnEventWhenPurchaseRequestIsValid() {
        //given
        TestItemFactory.ProductItemTestData testData = TestItemFactory.createTestData(
                10, 10, 10, 10, 10, 10,
                1, 1, 1, 1, 1, 1
        );
        UserProductItem userProductItem = testData.getUserProductItem();

        //when
        Input input = Input.builder()
                .productItem(testData.getProductItem())
                .buyProductItem(userProductItem)
                .build();
        Event event = service.execute(input);
        UserProductItem buyProductItem = event.getBuyProductItem();

        //then
        softly.assertThat(event).isNotNull();
        softly.assertThat(buyProductItem.getItemId()).isEqualTo(userProductItem.getItemId());
        softly.assertThat(buyProductItem.getBasicOption().getOptionGroupId())
                .isEqualTo(userProductItem.getBasicOption().getOptionGroupId());
        softly.assertThat(buyProductItem.getAdditionalOption().getOptionId())
                .isEqualTo(userProductItem.getAdditionalOption().getOptionId());
        softly.assertAll();
    }

    @Test
    @DisplayName("구매 수량이 부족할 경우 예외가 발생합니다.")
    void shouldThrowExceptionWhenPurchaseQuantityIsNotAvailableToBuy() {
        //given
        ProductItem productItem = TestItemFactory.defaultProductItem();
        UserProductItem userProductItem = TestItemFactory.defaultUserProductItem();

        //when
        Input input = Input.builder()
                .productItem(productItem)
                .buyProductItem(userProductItem)
                .build();

        //then
        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ProductItemException.class);
    }

}
