package store.nightmarket.domain.item.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.InventoryException;
import store.nightmarket.domain.item.valueobject.InventoryId;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.UserId;

public class Inventory extends BaseModel<InventoryId> {

    UserId seller;
    List<InventoryProduct> inventory;

    private Inventory(
        InventoryId id,
        UserId seller
    ) {
        super(id);
        this.seller = seller;
        inventory = new ArrayList<>();
    }

    public static Inventory newInstance(
        InventoryId id,
        UserId seller
    ) {
        return new Inventory(
            id,
            seller
        );
    }

    public void add(InventoryProduct product) {
        inventory.add(product);
    }

    public void remove(InventoryProduct product) {
        if(!inventory.contains(product)) {
            throw new InventoryException("Product not in Inventory");
        }
        inventory.remove(product);
    }

    public String getErrorMessages(Cart cart) {
        Map<ProductVariantId, InventoryProduct> inventoryMap = inventory.stream()
            .collect(Collectors.toMap(InventoryProduct::getProductVariantId, Function.identity()));

        return cart.getShoppingBasket().stream()
            .map(buyProduct -> {
                InventoryProduct inventoryProduct = inventoryMap.get(buyProduct.getVariantId());
                return (inventoryProduct == null)
                    ? "재고 정보 없음: " + buyProduct.getVariantId() + "\n"
                    : inventoryProduct.getQuantityErrorMessage(buyProduct.getQuantity()).orElse("");
            })
            .filter(msg -> !msg.isEmpty())
            .collect(Collectors.joining());
    }

}
