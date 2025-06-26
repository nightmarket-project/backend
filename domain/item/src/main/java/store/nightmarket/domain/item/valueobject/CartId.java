package store.nightmarket.domain.item.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class CartId extends BaseId<UUID> {

    private UUID id;

    public CartId(UUID id) {
        this.id = id;
    }

}
