package store.nightmarket.itemcore.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class ItemOptionId extends BaseId<UUID> {

    private final UUID id;

    public ItemOptionId(UUID id) {
        this.id = id;
    }

}
