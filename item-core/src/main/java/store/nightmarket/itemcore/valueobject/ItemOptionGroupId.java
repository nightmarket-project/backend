package store.nightmarket.itemcore.valueobject;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

import java.util.Objects;
import java.util.UUID;

@Getter
public class ItemOptionGroupId extends BaseId<UUID> {

    private final UUID id;

    public ItemOptionGroupId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemOptionGroupId other = (ItemOptionGroupId) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
