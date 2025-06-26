package store.nightmarket.domain.item.valueobject;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class VariantOptionValueId extends BaseId<UUID> {

    private final UUID id;

    public VariantOptionValueId(UUID id) {
        this.id = id;
    }

}
