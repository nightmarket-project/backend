package store.nightmarket.domain.delivery.service;

import static org.assertj.core.api.Assertions.*;
import static store.nightmarket.domain.delivery.service.dto.AddDeliveryTrackRecordDomainServiceDto.*;
import static store.nightmarket.domain.delivery.util.DeliveryTestUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;

public class AddDeliveryTrackRecordDomainServiceTest {

	@Test
	@DisplayName("배송 추적 기록 추가 시 현재 상태로 반영된다")
	void addTrackingRecordShouldUpdateCurrentRecord() {
		// given
		DeliveryTrackingRecord deliveryTrackingRecord1 = makeDeliveryTrackingRecord(
			UUID.randomUUID(),
			"출하지",
			DetailDeliveryState.SHIPPED,
			"재고가 출하 되었습니다."
		);
		DeliveryTrackingRecord deliveryTrackingRecord2 = makeDeliveryTrackingRecord(
			UUID.randomUUID(),
			"HUB",
			DetailDeliveryState.IN_DELIVERY,
			"물류 허브에 도착했습니다."
		);

		List<DeliveryTrackingRecord> list = new ArrayList<>();
		list.add(deliveryTrackingRecord2);

		DeliveryRecord deliveryRecord = makeDeliveryRecord(list);

		Input input = makeAddDeliveryTrackInput(deliveryRecord, deliveryTrackingRecord2);

		AddDeliveryTrackRecordDomainService service = new AddDeliveryTrackRecordDomainService();

		// when
		Event event = service.execute(input);

		// then
		DeliveryRecord addedRecord = event.getDeliveryRecord();
		DeliveryTrackingRecord currentRecord = addedRecord.getCurrentRecord();

		assertThat(currentRecord).isEqualTo(deliveryTrackingRecord2);
	}

}
