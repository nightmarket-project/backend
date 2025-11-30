package store.nightmarket.domain.delivery.model.id;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class DeliveryRecordId extends BaseId<UUID> {

    private final UUID id;

    public DeliveryRecordId(UUID id) {
        this.id = id;
    }

}
