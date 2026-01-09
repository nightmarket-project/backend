package store.nightmarket.domain.payment.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import store.nightmarket.domain.payment.exception.PaymentException;
import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.service.dto.CancelDetailPaymentDomainServiceDto.Event;
import store.nightmarket.domain.payment.service.dto.CancelDetailPaymentDomainServiceDto.Input;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.util.PaymentTestUtil;

class CancelDetailPaymentDomainServiceTest {

	private SoftAssertions softly;
	private CancelDetailPaymentDomainService service;

	@BeforeEach
	void setUp() {
		softly = new SoftAssertions();
		service = new CancelDetailPaymentDomainService();
	}

	@Test
	@DisplayName("일부 DetailPayment가 SUBMITTED 상태일 때 CANCELED 상태로 전이되어야 한다")
	void shouldTransitionSomeDetailPaymentsFromSubmittedToCanceled() {
		//given
		DetailPaymentRecord testDetailPayment = PaymentTestUtil.createTestDetailPayment(
			UUID.randomUUID(),
			PaymentTestUtil.createTestProduct(
				UUID.randomUUID(),
				1000
			),
			DetailPaymentState.SUBMITTED
		);
		PaymentRecord testPayment = PaymentTestUtil.createTestPayment(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			List.of(
				testDetailPayment,
				PaymentTestUtil.createTestDetailPayment(
					UUID.randomUUID(),
					PaymentTestUtil.createTestProduct(
						UUID.randomUUID(),
						2000
					),
					DetailPaymentState.SUBMITTED
				)
			)
		);

		Input input = Input.builder()
			.paymentRecord(testPayment)
			.detailPaymentRecord(testDetailPayment)
			.build();

		//when
		Event event = service.execute(input);

		List<DetailPaymentRecord> detailPaymentRecordList = event.getPaymentRecord()
			.getDetailPaymentRecordList();

		softly.assertThat(detailPaymentRecordList.getFirst().getState())
			.isEqualTo(DetailPaymentState.CANCELED);
		softly.assertThat(detailPaymentRecordList.getLast().getState())
			.isEqualTo(DetailPaymentState.SUBMITTED);
		softly.assertAll();
	}

	@MethodSource("invalidTransitions")
	@ParameterizedTest(name = "현재 상태가 {0}일 때 CANCELED 전이 시도 시 예외 발생")
	@DisplayName("유효하지 않은 상태에서 CANCELED로 전이 시 PaymentException 발생해야 함")
	void shouldThrowExceptionWhenTransitionToCanceledFromInvalidState(DetailPaymentState currentState) {
		//given
		DetailPaymentRecord testDetailPayment = PaymentTestUtil.createTestDetailPayment(
			UUID.randomUUID(),
			PaymentTestUtil.createTestProduct(
				UUID.randomUUID(),
				1000
			),
			currentState
		);
		PaymentRecord testPayment = PaymentTestUtil.createTestPayment(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			List.of(
				testDetailPayment,
				PaymentTestUtil.createTestDetailPayment(
					UUID.randomUUID(),
					PaymentTestUtil.createTestProduct(
						UUID.randomUUID(),
						2000
					),
					DetailPaymentState.SUBMITTED
				)
			)
		);
		Input input = Input.builder()
			.paymentRecord(testPayment)
			.detailPaymentRecord(testDetailPayment)
			.build();

		//when
		//then
		softly.assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(PaymentException.class);
	}

	private static Stream<Arguments> invalidTransitions() {
		return Stream.of(
			Arguments.of(DetailPaymentState.NONE),
			Arguments.of(DetailPaymentState.REJECTED),
			Arguments.of(DetailPaymentState.CANCELED),
			Arguments.of(DetailPaymentState.COMPLETED)
		);
	}

}
