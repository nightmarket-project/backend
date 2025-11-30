package store.nightmarket.domain.order.model.id;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class OrderRecordId extends BaseId<UUID> {

    private final UUID id;

    public OrderRecordId(UUID id) {
        this.id = id;
    }

}
