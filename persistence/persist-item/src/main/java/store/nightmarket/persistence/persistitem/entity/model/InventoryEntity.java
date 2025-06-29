package store.nightmarket.persistence.persistitem.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;

@Getter
@Entity
@Table(name = "inventory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryEntity extends BaseUuidEntity {

    @OneToMany(mappedBy = "inventoryEntity", fetch = FetchType.LAZY)
    private List<ProductVariantEntity> inventory = new ArrayList<>();

    private InventoryEntity(List<ProductVariantEntity> inventory) {
        this.inventory = inventory;
    }

    public static InventoryEntity newInstance(List<ProductVariantEntity> inventory) {
        return new InventoryEntity(inventory);
    }

}
