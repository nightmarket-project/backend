package store.nightmarket.domain.item.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.fixture.TestItemFactory;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.exception.QuantityException;
import store.nightmarket.itemcore.model.ItemDetailOption;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.UserItemDetailOption;
import store.nightmarket.itemcore.model.UserItemOption;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductItemTest {

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("아이템 제품 수량이 요청 수량보다 많을때 Optional empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenProductQuantityIsSufficient() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                5, 5, 5, 5, 5, 5);

        // when
        Optional<List<ErrorResult>> productItemErrors = testProductItem.findProductItemErrors(testUserBuyProductItem);

        // then
        assertThat(productItemErrors)
                .isEmpty();
    }

    @Test
    @DisplayName("3개 세부 옵션 수량이 요청 수량보다 적을때 ErrorResult 3개를 반환한다.")
    void shouldReturnErrorResultsWhenProductQuantityIsInsufficient() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                15, 5, 15, 5, 15, 5);

        // when
        Optional<List<ErrorResult>> productItemErrors = testProductItem.findProductItemErrors(testUserBuyProductItem);


        // then
        productItemErrors.ifPresent(
                errorResults -> {
                    softly.assertThat(errorResults).isNotEmpty();
                    softly.assertThat(errorResults).hasSize(3);

                    List<UserItemOption> userBasicOptions = testUserBuyProductItem.getBasicOption().getUserItemOptions();
                    List<UserItemDetailOption> userAdditionalOptions = testUserBuyProductItem.getAdditionalOption().getUserItemDetailOptions();
                    softly.assertThat(errorResults.getFirst().optionId()).isEqualTo(
                            userBasicOptions.getFirst().getUserItemDetailOptions().getFirst().getDetailOptionId());
                    softly.assertThat(errorResults.get(1).optionId()).isEqualTo(
                            userBasicOptions.getLast().getUserItemDetailOptions().getFirst().getDetailOptionId());
                    softly.assertThat(errorResults.getLast().optionId()).isEqualTo(
                            userAdditionalOptions.getFirst().getDetailOptionId());
                }
        );

        softly.assertAll();
    }

    @Test
    @DisplayName("product 수량이 요청 수량보다 많아서 제품 수량이 감소한다")
    void shouldReduceProductQuantityWhenProductQuantityIsSufficient() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                5, 5, 5, 5, 5, 5);

        // when
        testProductItem.reduceProductBy(testUserBuyProductItem);

        // then
        Quantity quantity = new Quantity(new BigDecimal(5));
        List<ItemOption> basicOptions = testProductItem.getBasicOption().getItemOptions();
        List<ItemDetailOption> colorOption = basicOptions.getFirst().getItemDetailOptions();
        List<ItemDetailOption> cpuOption = basicOptions.getLast().getItemDetailOptions();
        List<ItemDetailOption> additionalOptions = testProductItem.getAdditionalOption().getItemDetailOptions();

        softly.assertThat(colorOption.getFirst())
                .extracting("quantity")
                .isEqualTo(quantity);
        softly.assertThat(colorOption.getLast())
                .extracting("quantity")
                .isEqualTo(quantity);
        softly.assertThat(cpuOption.getFirst())
                .extracting("quantity")
                .isEqualTo(quantity);
        softly.assertThat(cpuOption.getLast())
                .extracting("quantity")
                .isEqualTo(quantity);
        softly.assertThat(additionalOptions.getFirst())
                .extracting("quantity")
                .isEqualTo(quantity);
        softly.assertThat(additionalOptions.getLast())
                .extracting("quantity")
                .isEqualTo(quantity);

        softly.assertAll();
    }

    @Test
    @DisplayName("product 수량이 요청 수량보다 적어서 수량 예외가 발생한다.")
    void shouldReturnQuantityErrorWhenProductQuantityIsInsufficient() {
        // given
        TestItemFactory factory = new TestItemFactory();
        ProductItem testProductItem = factory.createTestProductItem(
                10, 10, 10, 10, 10, 10);
        UserBuyProductItem testUserBuyProductItem = factory.createTestUserBuyProductItem(
                5, 5, 15, 5, 5, 5);


        // when & then
        assertThatThrownBy(() -> testProductItem.reduceProductBy(testUserBuyProductItem))
                .isInstanceOf(QuantityException.class);
    }

}
