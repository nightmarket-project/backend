package store.nightmarket.domain.item.valueobject;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class InventoryId extends BaseId<UUID> {

    private final UUID id;

    public InventoryId(UUID id) {
        this.id = id;
    }

}
