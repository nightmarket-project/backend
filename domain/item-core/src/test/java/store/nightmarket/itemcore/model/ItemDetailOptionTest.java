package store.nightmarket.itemcore.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.exception.QuantityException;
import store.nightmarket.itemcore.fixture.TestOptionFactory;
import store.nightmarket.itemcore.fixture.TestUserOptionFactory;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemDetailOptionTest {

    SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("아이템 세부 옵션 수량이 요청 수량보다 많을때 Optional empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenDetailOptionQuantityIsSufficient() {
        // given
        DetailOptionTestData testData = createTestData(15);

        // when
        Optional<ErrorResult> error = testData.detailOption.findDetailOptionError(testData.userItemDetailOption);

        // then
        assertThat(error.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("아이템 수량이 요청 수량보다 부족할때 ErrorResult를 반환한다.")
    void shouldReturnErrorResultWhenItemDetailQuantityIsInsufficient() {
        // given
        DetailOptionTestData testData = createTestData(5);

        // when
        Optional<ErrorResult> error = testData.detailOption.findDetailOptionError(testData.userItemDetailOption);
        // then
        error.ifPresent(
                errorResult -> {
                    softly.assertThat(errorResult).isNotNull();
                    softly.assertThat(errorResult.optionId()).isEqualTo(testData.detailOption.getDetailOptionId());
                    softly.assertThat(errorResult.message()).isEqualTo("보유 수량이 요청 수량보다 작다.");
                }
        );
        softly.assertAll();
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 많을때 옵션수량은 요청수량만큼 감소한다.")
    void shouldReduceDetailOptionQuantityWhenDetailOptionIsSufficient() {
        // given
        DetailOptionTestData testData = createTestData(15);

        // when
        testData.detailOption.reduceDetailOptionQuantityBy(testData.userItemDetailOption);

        // then
        Quantity quantity = new Quantity(new BigDecimal(5));
        assertThat(testData.detailOption.getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 적을때 수량 오류 예외가 발생한다.")
    void shouldThrowQuantityErrorWhenDetailOptionQuantityIsInsufficient() {
        // given
        DetailOptionTestData testData = createTestData(5);

        // when & then
        assertThatThrownBy(() ->  testData.detailOption.reduceDetailOptionQuantityBy(testData.userItemDetailOption))
                .isInstanceOf(QuantityException.class);
    }

    private DetailOptionTestData createTestData(int quantity) {
        UUID uuid = UUID.randomUUID();
        ItemDetailOption detailOption = TestOptionFactory.createDetailOption(
                uuid,
                "하얀색",
                500,
                quantity
        );
        UserItemDetailOption userItemDetailOption = TestUserOptionFactory.createUserItemDetailOption(
                uuid,
                10
        );

        return new DetailOptionTestData(detailOption, userItemDetailOption);
    }

    private record DetailOptionTestData(ItemDetailOption detailOption, UserItemDetailOption userItemDetailOption) {}

}
