package store.nightmarket.domain.item.model.id;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class OptionValueId extends BaseId<UUID> {

    private final UUID id;

    public OptionValueId(UUID id) {
        this.id = id;
    }

}
