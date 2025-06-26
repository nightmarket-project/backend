package store.nightmarket.domain.item.valueobject;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class OptionGroupId extends BaseId<UUID> {

    private final UUID id;

    public OptionGroupId(UUID id) {
        this.id = id;
    }

}
