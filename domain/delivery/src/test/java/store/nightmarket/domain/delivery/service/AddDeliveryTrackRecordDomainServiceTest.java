package store.nightmarket.domain.delivery.service;

import static store.nightmarket.domain.delivery.service.dto.AddDeliveryTrackRecordDomainServiceDto.*;
import static store.nightmarket.domain.delivery.util.DeliveryTestUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import store.nightmarket.domain.delivery.exception.DeliveryException;
import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;

public class AddDeliveryTrackRecordDomainServiceTest {

	private SoftAssertions softly;
	private AddDeliveryTrackRecordDomainService service;

	@BeforeEach
	void setUp() {
		softly = new SoftAssertions();
		service = new AddDeliveryTrackRecordDomainService();
	}

	@ParameterizedTest
	@MethodSource("validTransitions")
	@DisplayName("허용된 상태 전이는 반드시 최신 상태를 반영해야 한다.")
	void addTrackingRecordShouldUpdateCurrentRecord(DetailDeliveryState currentState, DetailDeliveryState nextState) {
		// given
		DeliveryTrackingRecord currentRecord = makeDeliveryTrackingRecord(
			UUID.randomUUID(),
			"장소1",
			currentState,
			currentState.name()
		);
		DeliveryTrackingRecord nextRecord = makeDeliveryTrackingRecord(
			UUID.randomUUID(),
			"장소2",
			nextState,
			nextState.name()
		);

		DeliveryRecord deliveryRecord = makeDeliveryRecord(new ArrayList<>(List.of(currentRecord)));

		Input input = makeAddDeliveryTrackInput(deliveryRecord, nextRecord);

		// when
		Event event = service.execute(input);

		// then
		DeliveryRecord executedDeliveryRecord = event.getDeliveryRecord();
		DeliveryTrackingRecord executedCurrentRecord = executedDeliveryRecord.getLatestRecord();

		softly.assertThat(executedCurrentRecord.getState()).isEqualTo(nextRecord.getState());
		softly.assertThat(executedDeliveryRecord.getDeliveryTrackingRecordList().size()).isEqualTo(2);

		softly.assertAll();
	}

	private static Stream<Arguments> validTransitions() {
		return Stream.of(
			Arguments.of(DetailDeliveryState.NONE, DetailDeliveryState.PREPARING),
			Arguments.of(DetailDeliveryState.PREPARING, DetailDeliveryState.SHIPPED),
			Arguments.of(DetailDeliveryState.SHIPPED, DetailDeliveryState.IN_DELIVERY),
			Arguments.of(DetailDeliveryState.IN_DELIVERY, DetailDeliveryState.IN_DELIVERY),
			Arguments.of(DetailDeliveryState.IN_DELIVERY, DetailDeliveryState.DELIVERED),
			Arguments.of(DetailDeliveryState.DELIVERED, DetailDeliveryState.RETURNED)
		);
	}

	@ParameterizedTest
	@MethodSource("invalidTransitions")
	@DisplayName("허용되지 않은 상태 전이는 예외를 던진다")
	void throwExceptionWhenInvalidTransition(DetailDeliveryState currentState, DetailDeliveryState nextState) {
		// given
		DeliveryTrackingRecord currentRecord = makeDeliveryTrackingRecord(
			UUID.randomUUID(),
			"장소1",
			currentState,
			currentState.name()
		);
		DeliveryTrackingRecord nextRecord = makeDeliveryTrackingRecord(
			UUID.randomUUID(),
			"장소2",
			nextState,
			nextState.name()
		);

		DeliveryRecord deliveryRecord = makeDeliveryRecord(new ArrayList<>(List.of(currentRecord)));

		Input input = makeAddDeliveryTrackInput(deliveryRecord, nextRecord);

		// when & then
		softly.assertThatThrownBy(() -> service.execute(input))
			.isInstanceOf(DeliveryException.class)
			.hasMessageContaining("Cannot change to");

		softly.assertAll();
	}

	private static Stream<Arguments> invalidTransitions() {
		return Stream.of(
			Arguments.of(DetailDeliveryState.PREPARING, DetailDeliveryState.PREPARING),
			Arguments.of(DetailDeliveryState.SHIPPED, DetailDeliveryState.PREPARING),
			Arguments.of(DetailDeliveryState.IN_DELIVERY, DetailDeliveryState.PREPARING),
			Arguments.of(DetailDeliveryState.DELIVERED, DetailDeliveryState.PREPARING),
			Arguments.of(DetailDeliveryState.CANCELLED, DetailDeliveryState.PREPARING),
			Arguments.of(DetailDeliveryState.RETURNED, DetailDeliveryState.PREPARING),

			Arguments.of(DetailDeliveryState.SHIPPED, DetailDeliveryState.SHIPPED),
			Arguments.of(DetailDeliveryState.IN_DELIVERY, DetailDeliveryState.SHIPPED),
			Arguments.of(DetailDeliveryState.DELIVERED, DetailDeliveryState.SHIPPED),
			Arguments.of(DetailDeliveryState.CANCELLED, DetailDeliveryState.SHIPPED),
			Arguments.of(DetailDeliveryState.RETURNED, DetailDeliveryState.SHIPPED),

			Arguments.of(DetailDeliveryState.PREPARING, DetailDeliveryState.IN_DELIVERY),
			Arguments.of(DetailDeliveryState.DELIVERED, DetailDeliveryState.IN_DELIVERY),
			Arguments.of(DetailDeliveryState.CANCELLED, DetailDeliveryState.IN_DELIVERY),
			Arguments.of(DetailDeliveryState.RETURNED, DetailDeliveryState.IN_DELIVERY),

			Arguments.of(DetailDeliveryState.PREPARING, DetailDeliveryState.DELIVERED),
			Arguments.of(DetailDeliveryState.SHIPPED, DetailDeliveryState.DELIVERED),
			Arguments.of(DetailDeliveryState.DELIVERED, DetailDeliveryState.DELIVERED),
			Arguments.of(DetailDeliveryState.CANCELLED, DetailDeliveryState.DELIVERED),
			Arguments.of(DetailDeliveryState.RETURNED, DetailDeliveryState.DELIVERED)
		);
	}

}
