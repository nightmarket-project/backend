package store.nightmarket.itemcore.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.Objects;
import java.util.UUID;

@Getter
public class RegistrantId extends BaseId<UUID> {

    private final UUID id;

    public RegistrantId(UUID id) {
        this.id = id;
    }

}
