package store.nightmarket.domain.item.model;

import java.time.LocalDate;
import java.util.Optional;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.InventoryProductId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;

@Getter
public class InventoryProduct extends BaseModel<InventoryProductId> {

    private ProductVariantId productVariantId;
    private Name name;
    private Quantity quantity;
    private LocalDate lastUpdate;

    public InventoryProduct(
        InventoryProductId id,
        ProductVariantId productVariantId,
        Name name,
        Quantity quantity,
        LocalDate lastUpdate
    ) {
        super(id);
        this.productVariantId = productVariantId;
        this.name = name;
        this.quantity = quantity;
        this.lastUpdate = lastUpdate;
    }

    public InventoryProduct newInstance(
        InventoryProductId id,
        ProductVariantId productVariantId,
        Name name,
        Quantity quantity,
        LocalDate lastUpdate
    ) {
        return new InventoryProduct(
            id,
            productVariantId,
            name,
            quantity,
            lastUpdate
        );
    }

    public InventoryProductId getId() {
        return internalId();
    }

    public Optional<String> getQuantityErrorMessage(Quantity buyQuantity) {
        if(quantity.isLessThan(buyQuantity)) {
            return Optional.of("Not enough stock: " + name.getValue() + "\n");
        }
        return Optional.empty();
    }

}
