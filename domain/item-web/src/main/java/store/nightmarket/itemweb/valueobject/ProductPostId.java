package store.nightmarket.itemweb.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.Objects;
import java.util.UUID;

@Getter
public class ProductPostId extends BaseId<UUID> {

    private final UUID id;

    public ProductPostId(UUID id) {
        this.id = id;
    }

}
