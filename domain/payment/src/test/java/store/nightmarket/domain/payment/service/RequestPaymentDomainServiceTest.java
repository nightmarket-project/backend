package store.nightmarket.domain.payment.service;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import store.nightmarket.domain.payment.exception.PaymentException;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.util.PaymentTestUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static store.nightmarket.domain.payment.service.dto.RequestPaymentDomainServiceDto.*;

class RequestPaymentDomainServiceTest {

    private RequestPaymentDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new RequestPaymentDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("모든 DetailPayment가 NONE 상태일 때 SUBMITTED 상태로 전이되어야 한다")
    void shouldTransitionAllDetailPaymentsFromNoneToSubmitted() {
        // given
        PaymentRecord testPayment = PaymentTestUtil.createTestPayment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of(
                        PaymentTestUtil.createTestDetailPayment(
                                UUID.randomUUID(),
                                PaymentTestUtil.createTestProduct(
                                        UUID.randomUUID(),
                                        1000
                                ),
                                DetailPaymentState.NONE
                        ),
                        PaymentTestUtil.createTestDetailPayment(
                                UUID.randomUUID(),
                                PaymentTestUtil.createTestProduct(
                                        UUID.randomUUID(),
                                        2000
                                ),
                                DetailPaymentState.NONE
                        )
                )
        );

        Input input = Input.builder()
                .paymentRecord(testPayment)
                .build();

        // when
        Event event = service.execute(input);

        // then
        PaymentRecord executePaymentRecord = event.getPaymentRecord();

        executePaymentRecord.getDetailPaymentRecordList()
                .forEach(detailPaymentRecord ->
                        softly.assertThat(detailPaymentRecord.getState())
                                .isEqualTo(DetailPaymentState.SUBMITTED)
                );
        softly.assertAll();
    }

    @MethodSource("invalidTransitions")
    @ParameterizedTest(name = "현재 상태가 {0}일 때 SUBMITTED로 전이 시도 시 예외 발생")
    @DisplayName("유효하지 않은 상태에서 SUBMITTED로 전이 시 PaymentException 발생해야 함")
    void shouldThrowExceptionWhenTransitionToSubmittedFromInvalidState (DetailPaymentState currentState) {
        // given
        PaymentRecord testPayment = PaymentTestUtil.createTestPayment(
                UUID.randomUUID(),
                UUID.randomUUID(),
                List.of(
                        PaymentTestUtil.createTestDetailPayment(
                                UUID.randomUUID(),
                                PaymentTestUtil.createTestProduct(
                                        UUID.randomUUID(),
                                        1000
                                ),
                                currentState
                        ),
                        PaymentTestUtil.createTestDetailPayment(
                                UUID.randomUUID(),
                                PaymentTestUtil.createTestProduct(
                                        UUID.randomUUID(),
                                        2000
                                ),
                                currentState
                        )
                )
        );

        Input input = Input.builder()
                .paymentRecord(testPayment)
                .build();

        // when
        // then
        softly.assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(PaymentException.class);
    }

    private static Stream<Arguments> invalidTransitions() {
        return Stream.of(
                Arguments.of(DetailPaymentState.SUBMITTED),
                Arguments.of(DetailPaymentState.REJECTED),
                Arguments.of(DetailPaymentState.CANCELED),
                Arguments.of(DetailPaymentState.COMPLETED)
        );
    }


}
