package store.nightmarket.itemcore.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.exception.QuantityException;
import store.nightmarket.itemcore.fixture.TestOptionFactory;
import store.nightmarket.itemcore.fixture.TestUserOptionFactory;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class
ItemOptionTest {

    private SoftAssertions softly;
    private UUID optionColorId;
    private UUID blackId;
    private UUID whiteId;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        optionColorId = UUID.randomUUID();
        blackId = UUID.randomUUID();
        whiteId = UUID.randomUUID();
    }

    @Test
    @DisplayName("아이템 수량이 요청 수량보다 많을때 List empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenOptionQuantityIsSufficient() {
        // given
        ItemOption testItemOption = createTestItemOption(10, 10);
        UserItemOption testUserItemOption = createTestUserItemOption(5, 5);

        // when
        List<ErrorResult> errors = testItemOption.findOptionErrors(testUserItemOption);

        // then
        assertThat(errors).isEmpty();
    }

    @Test
    @DisplayName("2개 아이템 수량이 2개 요청 수량보다 적을때 ErrorResult 2개를 반환한다.")
    void shouldReturnErrorResultsWhenOptionQuantityIsInsufficient() {
        // given
        ItemOption testItemOption = createTestItemOption(10, 10);
        UserItemOption testUserItemOption = createTestUserItemOption(15, 15);

        // when
        List<ErrorResult> errors = testItemOption.findOptionErrors(testUserItemOption);

        // then
        softly.assertThat(errors).isNotEmpty();
        softly.assertThat(errors).hasSize(2);
        softly.assertThat(errors.getFirst().optionId()).isEqualTo(
                testUserItemOption.getUserItemDetailOptions().getFirst().getDetailOptionId());
        softly.assertThat(errors.getLast().optionId()).isEqualTo(
                testUserItemOption.getUserItemDetailOptions().getLast().getDetailOptionId());

        softly.assertAll();
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 많을때 옵션수량은 요청수량만큼 감소한다.")
    void shouldReduceDetailOptionQuantityWhenOptionIsSufficient() {
        // given
        ItemOption testItemOption = createTestItemOption(10, 10);
        UserItemOption testUserItemOption = createTestUserItemOption(5, 5);

        // when
        testItemOption.reduceOptionQuantityBy(testUserItemOption);

        // then
        ItemOption resultTestItemOption = createTestItemOption(5, 5);

        assertThat(testItemOption).isEqualTo(resultTestItemOption);
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 적을때 수량 오류 예외가 발생한다.")
    void shouldThrowQuantityErrorWhenOptionIsInsufficient() {
        // given
        ItemOption testItemOption = createTestItemOption(10, 10);
        UserItemOption testUserItemOption = createTestUserItemOption(5, 15);

        // when & then
        assertThatThrownBy(() -> testItemOption.reduceOptionQuantityBy(testUserItemOption))
                .isInstanceOf(QuantityException.class);
    }

    private UserItemOption createTestUserItemOption(int blackUserQty, int whiteUserQty) {
        return TestUserOptionFactory.createUserItemOption(
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
    }

    private ItemOption createTestItemOption(int blackQty, int whiteQty) {
        return TestOptionFactory.createItemOption(
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
    }

}
