package store.nightmarket.domain.item.valueobject;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class InventoryProductId extends BaseId<UUID> {

    private final UUID id;

    public InventoryProductId(UUID id) {
        this.id = id;
    }

}
