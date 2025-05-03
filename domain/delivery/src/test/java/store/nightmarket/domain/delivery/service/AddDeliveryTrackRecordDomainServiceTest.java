package store.nightmarket.domain.delivery.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static store.nightmarket.domain.delivery.service.dto.AddDeliveryTrackRecordDomainServiceDto.*;

public class AddDeliveryTrackRecordDomainServiceTest {

    @Test
    @DisplayName("배송 추적 기록 추가 시 현재 상태로 반영된다")
    void addTrackingRecordShouldUpdateCurrentRecord() {
        // given
        DeliveryRecord deliveryRecord = deliveryRecord();
        DeliveryTrackingRecord deliveryTrackingRecord = deliveryTrackingRecord3();
        Input input = Input.builder()
                        .deliveryRecord(deliveryRecord)
                        .deliveryTrackingRecord(deliveryTrackingRecord)
                        .build();

        AddDeliveryTrackRecordDomainService service = new AddDeliveryTrackRecordDomainService();

        // when
        Event event = service.execute(input);
        DeliveryRecord addedRecord = event.getDeliveryRecord();
        DeliveryTrackingRecord currentRecord = addedRecord.getCurrentRecord();

        // then
        assertEquals(deliveryTrackingRecord, currentRecord);
    }

    private DeliveryTrackingRecord deliveryTrackingRecord1() {
        return DeliveryTrackingRecord.newInstance(
            new DeliveryTrackingRecordId(UUID.randomUUID()),
            LocalDateTime.of(2025,5,1, 15,15),
            new Location("HUB"),
            DetailDeliveryState.SHIPPED,
            "물류 허브에서 출고 되었습니다."
        );
    }

    private DeliveryTrackingRecord deliveryTrackingRecord2() {
        return DeliveryTrackingRecord.newInstance(
                new DeliveryTrackingRecordId(UUID.randomUUID()),
                LocalDateTime.of(2025,5,1, 16,16),
                new Location("LOCAL HUB"),
                DetailDeliveryState.IN_DELIVERY,
                "중간 허브를 거쳤습니다."
        );
    }

    private DeliveryTrackingRecord deliveryTrackingRecord3() {
        return DeliveryTrackingRecord.newInstance(
                new DeliveryTrackingRecordId(UUID.randomUUID()),
                LocalDateTime.of(2025,5,1, 16,16),
                new Location("배송지"),
                DetailDeliveryState.DELIVERED,
                "배송 완료"
        );
    }

    private DeliveryRecord deliveryRecord() {
        return DeliveryRecord.newInstance(
            new DeliveryRecordId(UUID.randomUUID()),
            new Address("111111","하늘로 111-111","구름아파트 111동 111호"),
            new UserId(UUID.randomUUID()),
            new ArrayList<>(List.of(deliveryTrackingRecord1(), deliveryTrackingRecord2()))
        );
    }

}
