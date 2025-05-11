package store.nightmarket.domain.delivery.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;

public class AddDeliveryTrackRecordDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final DeliveryRecord deliveryRecord;
        private final DeliveryTrackingRecord deliveryTrackingRecord;

    }

    @Getter
    @Builder
    public static class Event {

        private final DeliveryRecord deliveryRecord;

    }

}
