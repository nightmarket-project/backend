package store.nightmarket.domain.item.valueobject;

import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class ShoppingBasketProductId extends BaseId<UUID> {

    private final UUID id;

    public ShoppingBasketProductId(UUID id) {
        this.id = id;
    }

}
