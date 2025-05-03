package store.nightmarket.domain.delivery.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.delivery.state.DetailDeliveryState;
import store.nightmarket.domain.delivery.valueobject.DeliveryTrackingRecordId;
import store.nightmarket.domain.delivery.valueobject.Location;

import java.time.LocalDateTime;

public class DeliveryTrackingRecord extends BaseModel<DeliveryTrackingRecordId> {

    private LocalDateTime time;
    private Location location;
    private DetailDeliveryState state;
    private String content;

    public DeliveryTrackingRecord(
            DeliveryTrackingRecordId id,
            LocalDateTime time,
            Location location,
            DetailDeliveryState state,
            String content
    ) {

        super(id);
        this.time = time;
        this.location = location;
        this.state = state;
        this.content = content;
    }

    public static DeliveryTrackingRecord newInstance(
        DeliveryTrackingRecordId id,
        LocalDateTime time,
        Location location,
        DetailDeliveryState state,
        String content
    ) {

        return new DeliveryTrackingRecord(
            id,
            time,
            location,
            state,
            content
        );
    }

}
