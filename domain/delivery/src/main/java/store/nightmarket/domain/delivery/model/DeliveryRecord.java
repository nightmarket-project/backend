package store.nightmarket.domain.delivery.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.delivery.valueobject.Address;
import store.nightmarket.domain.delivery.valueobject.DeliveryRecordId;
import store.nightmarket.domain.delivery.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRecord extends BaseModel<DeliveryRecordId> {

    private Address address;
    private UserId userId;
    private List<DeliveryTrackingRecord> deliveryTrackingRecordList;

    public DeliveryRecord(
            DeliveryRecordId id,
            Address address,
            UserId userId,
            List<DeliveryTrackingRecord> deliveryTrackingRecordList
    ) {

        super(id);
        this.address = address;
        this.userId = userId;
        this.deliveryTrackingRecordList = deliveryTrackingRecordList != null ? deliveryTrackingRecordList : new ArrayList<DeliveryTrackingRecord>();
    }

    public static DeliveryRecord newInstance(
            DeliveryRecordId id,
            Address address,
            UserId userId,
            List<DeliveryTrackingRecord> deliveryTrackingRecordList
    ) {

        return new DeliveryRecord(
            id,
            address,
            userId,
            deliveryTrackingRecordList
        );
    }

    public DeliveryTrackingRecord getCurrentRecord() {
        return deliveryTrackingRecordList.isEmpty() ? null : deliveryTrackingRecordList.getLast();
    }

    public void addDeliveryTrackingRecord(DeliveryTrackingRecord record) {
        this.deliveryTrackingRecordList.add(record);
    }

}
