package store.nightmarket.domain.item.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class ShoppingBasketId extends BaseId<UUID> {

    private UUID id;

    public ShoppingBasketId(UUID id) {
        this.id = id;
    }

}
