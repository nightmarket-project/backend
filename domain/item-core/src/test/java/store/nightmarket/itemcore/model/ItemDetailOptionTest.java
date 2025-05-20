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
    private UUID detailOptionId;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        detailOptionId = UUID.randomUUID();
    }

    @Test
    @DisplayName("아이템 세부 옵션 수량이 요청 수량보다 많을때 Optional empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenDetailOptionQuantityIsSufficient() {
        // given
        ItemDetailOption detailOption = createTestDetailOption(detailOptionId, 15);
        UserItemDetailOption userItemDetailOption = createTestUserItemDetailOption(detailOptionId, 10);

        // when
        Optional<ErrorResult> error = detailOption.findDetailOptionError(userItemDetailOption);

        // then
        assertThat(error.isEmpty())
                .isTrue();
    }



    @Test
    @DisplayName("아이템 수량이 요청 수량보다 부족할때 ErrorResult를 반환한다.")
    void shouldReturnErrorResultWhenItemDetailQuantityIsInsufficient() {
        // given
        ItemDetailOption detailOption = createTestDetailOption(detailOptionId,5);
        UserItemDetailOption userItemDetailOption = createTestUserItemDetailOption(detailOptionId, 10);

        // when
        Optional<ErrorResult> error = detailOption.findDetailOptionError(userItemDetailOption);

        // then
        softly.assertThat(error.isEmpty()).isFalse();
        error.ifPresent(
                errorResult -> {
                    softly.assertThat(errorResult)
                            .isNotNull();
                    softly.assertThat(errorResult.optionId())
                            .isEqualTo(detailOption.getDetailOptionId());
                    softly.assertThat(errorResult.message())
                            .isEqualTo("보유 수량이 요청 수량보다 작다.");
                }
        );
        softly.assertAll();
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 많을때 옵션수량은 요청수량만큼 감소한다.")
    void shouldReduceDetailOptionQuantityWhenDetailOptionIsSufficient() {
        // given
        ItemDetailOption detailOption = createTestDetailOption(detailOptionId, 10);
        UserItemDetailOption userItemDetailOption = createTestUserItemDetailOption(detailOptionId, 5);

        // when
        detailOption.reduceDetailOptionQuantityBy(userItemDetailOption);

        // then
        Quantity testQuantity = new Quantity(BigDecimal.valueOf(5));

        assertThat(detailOption)
                .extracting("quantity")
                .isEqualTo(testQuantity);
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 적을때 수량 오류 예외가 발생한다.")
    void shouldThrowQuantityErrorWhenDetailOptionQuantityIsInsufficient() {
        // given
        ItemDetailOption detailOption = createTestDetailOption(detailOptionId, 5);
        UserItemDetailOption userItemDetailOption = createTestUserItemDetailOption(detailOptionId, 10);

        // when & then
        assertThatThrownBy(() ->  detailOption.reduceDetailOptionQuantityBy(userItemDetailOption))
                .isInstanceOf(QuantityException.class);
    }

    private ItemDetailOption createTestDetailOption(UUID uuid, int quantity) {
        return TestOptionFactory.createDetailOption(
                uuid,
                "하얀색",
                500,
                quantity
        );
    }

    private UserItemDetailOption createTestUserItemDetailOption(UUID uuid, int quantity) {
        return TestUserOptionFactory.createUserItemDetailOption(
                uuid,
                quantity
        );
    }

}
