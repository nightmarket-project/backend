package store.nightmarket.domain.item.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.UUID;

@Getter
public class UserId extends BaseId<UUID> {

    private final UUID id;

    public UserId(UUID id) {
        this.id = id;
    }

}
