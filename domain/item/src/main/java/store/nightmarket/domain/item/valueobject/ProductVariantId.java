package store.nightmarket.domain.item.valueobject;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseId;

@Getter
public class ProductVariantId extends BaseId<UUID> {

    private final UUID id;

    public ProductVariantId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        ProductVariantId that = (ProductVariantId) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

}
