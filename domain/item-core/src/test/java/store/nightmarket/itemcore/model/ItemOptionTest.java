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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ItemOptionTest {

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("아이템 수량이 요청 수량보다 많을때 Optional empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenOptionQuantityIsSufficient() {
        // given
        ItemOptionTestData testData = createTestData(10, 10, 5, 5);

        // when
        Optional<List<ErrorResult>> errors = testData.option.findErrors(testData.userOption);

        // then
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("2개 아이템 수량이 2개 요청 수량보다 적을때 ErrorResult 2개를 반환한다.")
    void shouldReturnErrorResultsWhenOptionQuantityIsInsufficient() {
        // given
        ItemOptionTestData testData = createTestData(10, 10, 15, 15);

        // when
        Optional<List<ErrorResult>> errors = testData.option.findErrors(testData.userOption);

        // then
        errors.ifPresent(
                errorResults -> {
                    softly.assertThat(errorResults).isNotEmpty();
                    softly.assertThat(errorResults).hasSize(2);
                    softly.assertThat(errorResults.getFirst().optionId()).isEqualTo(
                            testData.userOption.getUserItemDetailOptions().getFirst().getDetailOptionId());
                    softly.assertThat(errorResults.getLast().optionId()).isEqualTo(
                            testData.userOption.getUserItemDetailOptions().getLast().getDetailOptionId());

                }
        );
        softly.assertAll();
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 많을때 옵션수량은 요청수량만큼 감소한다.")
    void shouldReduceDetailOptionQuantityWhenOptionIsSufficient() {
        // given
        ItemOptionTestData testData = createTestData(10, 10, 5, 5);

        // when
        testData.option.reduceStockBy(testData.userOption);

        // then
        Quantity quantity = new Quantity(new BigDecimal(5));
        softly.assertThat(testData.option.getItemDetailOptions().getFirst().getQuantity()).isEqualTo(quantity);
        softly.assertThat(testData.option.getItemDetailOptions().getLast().getQuantity()).isEqualTo(quantity);
        softly.assertAll();
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 적을때 수량 오류 예외가 발생한다.")
    void shouldThrowQuantityErrorWhenOptionIsInsufficient() {
        // given
        ItemOptionTestData testData = createTestData(10, 10, 5, 15);

        // when & then
        assertThatThrownBy(() -> testData.option.reduceStockBy(testData.userOption))
                .isInstanceOf(QuantityException.class);
    }

    private ItemOptionTestData createTestData(
            int blackQty,
            int whiteQty,
            int blackUserQty,
            int whiteUserQty
    ) {
        UUID optionColorId = UUID.randomUUID();
        UUID blackId = UUID.randomUUID();
        UUID whiteId = UUID.randomUUID();

        ItemOption itemOption = TestOptionFactory.createItemOption(
                optionColorId,
                "색깔",
                List.of(
                        TestOptionFactory.createDetailOption(
                                blackId,
                                "검은색",
                                1000,
                                blackQty
                        ), TestOptionFactory.createDetailOption(
                                whiteId,
                                "하얀색",
                                2000,
                                whiteQty
                        )
                )
        );

        UserItemOption userItemOption = TestUserOptionFactory.createUserItemOption(
                optionColorId,
                List.of(
                        TestUserOptionFactory.createUserItemDetailOption(
                                blackId,
                                blackUserQty
                        ), TestUserOptionFactory.createUserItemDetailOption(
                                whiteId,
                                whiteUserQty
                        )
                )
        );

        return new ItemOptionTestData(itemOption, userItemOption);
    }

    private record ItemOptionTestData(ItemOption option, UserItemOption userOption) {
    }

}
