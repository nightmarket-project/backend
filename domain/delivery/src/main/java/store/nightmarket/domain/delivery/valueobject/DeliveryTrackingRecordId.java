package store.nightmarket.domain.delivery.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class DeliveryTrackingRecordId extends BaseId<UUID> {

    private final UUID id;

    public DeliveryTrackingRecordId(UUID id) {
        this.id = id;
    }

}
