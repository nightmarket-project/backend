package store.nightmarket.domain.item.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.exception.QuantityException;
import store.nightmarket.itemcore.model.*;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.nightmarket.domain.item.fixture.TestItemFactory.*;

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
        ProductItemTestData testData = createTestData(
                10, 10, 10, 10, 10, 10,
                5, 5, 5, 5, 5, 5
        );

        // when
        Optional<List<ErrorResult>> productItemErrors = testData.getProductItem().findProductItemErrors(testData.getUserProductItem());

        // then
        assertThat(productItemErrors).isEmpty();
    }

    @Test
    @DisplayName("3개 세부 옵션 수량이 요청 수량보다 적을때 ErrorResult 3개를 반환한다.")
    void shouldReturnErrorResultsWhenProductQuantityIsInsufficient() {
        // given
        ProductItemTestData testData = createTestData(
                10, 10, 10, 10, 10, 10,
                15, 5, 15, 5, 15, 5
        );

        // when
        Optional<List<ErrorResult>> productItemErrors = testData.getProductItem().findProductItemErrors(testData.getUserProductItem());


        // then
        productItemErrors.ifPresent(
                errorResults -> {
                    softly.assertThat(errorResults).isNotEmpty();
                    softly.assertThat(errorResults).hasSize(3);

                    List<UserItemOption> userBasicOptions = testData.getUserProductItem().getBasicOption().getUserItemOptions();
                    List<UserItemDetailOption> userAdditionalOptions = testData.getUserProductItem().getAdditionalOption().getUserItemDetailOptions();
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
        ProductItemTestData testData = createTestData(
                10, 10, 10, 10, 10, 10,
                5, 5, 5, 5, 5, 5
        );

        // when
        testData.getProductItem().reduceProductBy(testData.getUserProductItem());

        // then
        Quantity quantity = new Quantity(new BigDecimal(5));
        List<ItemOption> basicOptions = testData.getProductItem().getBasicOption().getItemOptions();
        List<ItemDetailOption> colorOption = basicOptions.getFirst().getItemDetailOptions();
        List<ItemDetailOption> cpuOption = basicOptions.getLast().getItemDetailOptions();
        List<ItemDetailOption> additionalOptions = testData.getProductItem().getAdditionalOption().getItemDetailOptions();

        softly.assertThat(colorOption.getFirst().getQuantity()).isEqualTo(quantity);
        softly.assertThat(colorOption.getLast().getQuantity()).isEqualTo(quantity);
        softly.assertThat(cpuOption.getFirst().getQuantity()).isEqualTo(quantity);
        softly.assertThat(cpuOption.getLast().getQuantity()).isEqualTo(quantity);
        softly.assertThat(additionalOptions.getFirst().getQuantity()).isEqualTo(quantity);
        softly.assertThat(additionalOptions.getLast().getQuantity()).isEqualTo(quantity);

        softly.assertAll();
    }

    @Test
    @DisplayName("product 수량이 요청 수량보다 적어서 수량 예외가 발생한다.")
    void shouldReturnQuantityErrorWhenProductQuantityIsInsufficient() {
        // given
        ProductItemTestData testData = createTestData(
                10, 10, 10, 10, 10, 10,
                5, 5, 15, 5, 5, 5
        );

        // when & then
        assertThatThrownBy(() -> testData.getProductItem().reduceProductBy(testData.getUserProductItem()))
                .isInstanceOf(QuantityException.class);
    }

}
