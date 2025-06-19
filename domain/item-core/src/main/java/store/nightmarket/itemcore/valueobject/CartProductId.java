package store.nightmarket.itemcore.valueobject;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class CartProductId extends BaseId<UUID> {

    private final UUID id;

    public CartProductId(UUID id) {
        this.id = id;
    }

}
