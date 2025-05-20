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

class ItemOptionGroupTest {

    private SoftAssertions softly;
    private UUID groupId;
    private UUID optionColorId;
    private UUID optionCpuId;
    private UUID blackId;
    private UUID whiteId;
    private UUID cpuId;


    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
        groupId = UUID.randomUUID();
        optionColorId = UUID.randomUUID();
        optionCpuId = UUID.randomUUID();
        blackId = UUID.randomUUID();
        whiteId = UUID.randomUUID();
        cpuId = UUID.randomUUID();
    }

    @Test
    @DisplayName("아이템그룹 수량이 요청 수량보다 많을때 Optional empty를 반환한다.")
    void shouldReturnOptionalEmptyWhenOptionGroupQuantityIsSufficient() {
        // given
        ItemOptionGroup testGroup = createTestGroup(10, 10, 10);
        UserItemOptionGroup testUserGroup = createTestUserGroup(5, 5, 5);

        // when
        Optional<List<ErrorResult>> optionGroupErrors = testGroup.findOptionGroupErrors(testUserGroup);

        // then
        assertThat(optionGroupErrors)
                .isEmpty();
    }

    @Test
    @DisplayName("2개 아이템 수량이 2개 요청 수량보다 적을때 ErrorResult 2개를 반환한다.")
    void shouldReturnErrorResultsWhenOptionGroupQuantityIsInsufficient() {
        // given
        ItemOptionGroup testGroup = createTestGroup(10, 10, 10);
        UserItemOptionGroup testUserGroup = createTestUserGroup(15, 5, 15);

        // when
        Optional<List<ErrorResult>> optionGroupErrors = testGroup.findOptionGroupErrors(testUserGroup);

        // then
        optionGroupErrors.ifPresent(
                errorResults -> {
                    softly.assertThat(errorResults).isNotEmpty();
                    softly.assertThat(errorResults).hasSize(2);

                    List<UserItemOption> userItemOptions = testUserGroup.getUserItemOptions();
                    softly.assertThat(errorResults.getFirst().optionId()).isEqualTo(
                            userItemOptions.getFirst()
                                    .getUserItemDetailOptions().getFirst().getDetailOptionId());
                    softly.assertThat(errorResults.getLast().optionId()).isEqualTo(
                            userItemOptions.getLast()
                                    .getUserItemDetailOptions().getFirst().getDetailOptionId());
                }
        );
        softly.assertAll();
    }

    @Test
    @DisplayName("옵션수량이 요청수량보다 많을때 옵션수량은 요청수량만큼 감소한다.")
    void shouldReduceOptionGroupQuantityWhenOptionGroupIsSufficient() {
        // given
        ItemOptionGroup testGroup = createTestGroup(10, 10, 10);
        UserItemOptionGroup testUserGroup = createTestUserGroup(5, 5, 5);

        // when
        testGroup.reduceOptionGroupQuantityBy(testUserGroup);

        // then
        Quantity quantity = new Quantity(new BigDecimal(5));
        List<ItemDetailOption> colorOption = testGroup.getItemOptions().getFirst().getItemDetailOptions();
        List<ItemDetailOption> cpuOption = testGroup.getItemOptions().getLast().getItemDetailOptions();

        assertThat(colorOption.getFirst())
                .extracting("quantity")
                .isEqualTo(quantity);
        assertThat(colorOption.getLast())
                .extracting("quantity")
                .isEqualTo(quantity);
        assertThat(cpuOption.getFirst())
                .extracting("quantity")
                .isEqualTo(quantity);

        softly.assertAll();
    }

    @Test
    @DisplayName("옵션 수량이 요청수량보다 적을때 수량 오류 예외가 발생한다.")
    void shouldThrowQuantityErrorWhenOptionGroupIsInsufficient() {
        // given
        ItemOptionGroup testGroup = createTestGroup(10, 10, 10);
        UserItemOptionGroup testUserGroup = createTestUserGroup(15, 5, 15);

        // when & then
        assertThatThrownBy(() -> testGroup.reduceOptionGroupQuantityBy(testUserGroup))
                .isInstanceOf(QuantityException.class);
    }

    private UserItemOptionGroup createTestUserGroup(int blackUserQty, int whiteUserQty, int cpuUserQty) {
        return TestUserOptionFactory.createUserItemOptionGroup(
                groupId,
                List.of(
                        TestUserOptionFactory.createUserItemOption(
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
                        ),
                        TestUserOptionFactory.createUserItemOption(
                                optionCpuId,
                                List.of(
                                        TestUserOptionFactory.createUserItemDetailOption(
                                                cpuId,
                                                cpuUserQty
                                        )
                                )
                        )
                )
        );
    }

    private ItemOptionGroup createTestGroup(int blackQty, int whiteQty, int cpuQty) {
        return TestOptionFactory.createItemOptionGroup(
                groupId,
                List.of(
                        TestOptionFactory.createItemOption(
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
                        ), TestOptionFactory.createItemOption(
                                optionCpuId,
                                "cpu",
                                List.of(
                                        TestOptionFactory.createDetailOption(
                                                cpuId,
                                                "4코어",
                                                3000,
                                                cpuQty
                                        )
                                )
                        )
                )
        );
    }

}
