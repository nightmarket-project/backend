package store.nightmarket.domain.item.fixture;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import store.nightmarket.domain.item.model.Inventory;
import store.nightmarket.domain.item.model.InventoryProduct;
import store.nightmarket.domain.item.valueobject.InventoryId;
import store.nightmarket.domain.item.valueobject.InventoryProductId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemcore.valueobject.UserId;

public class InventoryFactory {

    public static Inventory createInventory(
        UUID inventoryId,
        UUID sellerId
    ) {
        return Inventory.newInstance(
            new InventoryId(inventoryId),
            new UserId(sellerId)
        );
    }

    public static InventoryProduct createInventoryProduct(
        UUID inventoryProductId,
        UUID productVariantId,
        String name,
        int quantity,
        LocalDate timestamp
    ) {
        return new InventoryProduct(
            new InventoryProductId(inventoryProductId),
            new ProductVariantId(productVariantId),
            new Name(name),
            new Quantity(BigDecimal.valueOf(quantity)),
            timestamp
        );
    }

}
