package store.nightmarket.itemcore.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class ItemOptionCombinationId extends BaseId<UUID> {

    private final UUID id;

    public ItemOptionCombinationId(UUID id) {
        this.id = id;
    }

}
