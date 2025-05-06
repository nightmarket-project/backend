package store.nightmarket.itemcore.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class OptionId extends BaseId<UUID> {

    private final UUID id;

    public OptionId(UUID id) {
        this.id = id;
    }

}
