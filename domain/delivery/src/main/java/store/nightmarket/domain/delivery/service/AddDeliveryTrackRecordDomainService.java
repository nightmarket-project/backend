package store.nightmarket.domain.delivery.service;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.delivery.model.DeliveryRecord;
import store.nightmarket.domain.delivery.model.DeliveryTrackingRecord;

import static store.nightmarket.domain.delivery.service.dto.AddDeliveryTrackRecordDomainServiceDto.Event;
import static store.nightmarket.domain.delivery.service.dto.AddDeliveryTrackRecordDomainServiceDto.Input;

public class AddDeliveryTrackRecordDomainService implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        DeliveryRecord deliveryRecord = input.getDeliveryRecord();
        DeliveryTrackingRecord deliveryTrackingRecord = input.getDeliveryTrackingRecord();

        deliveryRecord.addDeliveryTrackingRecord(deliveryTrackingRecord);

        return Event.builder()
                .deliveryRecord(deliveryRecord)
                .build();
    }

}
