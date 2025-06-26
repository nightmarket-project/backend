package store.nightmarket.domain.item.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.InventoryException;
import store.nightmarket.domain.item.valueobject.InventoryId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;

@Getter
public class Inventory extends BaseModel<InventoryId> {

    Map<ProductVariantId, ProductVariant> inventory;

    private Inventory(
        InventoryId id,
        List<ProductVariant> inventory
    ) {
        super(id);
        this.inventory = inventory.stream()
            .collect(Collectors.toMap(ProductVariant::getProductVariantId, Function.identity()));
    }

    public static Inventory newInstance(
        InventoryId id,
        List<ProductVariant> inventory
    ) {
        return new Inventory(
            id,
            inventory
        );
    }

    public void add(ProductVariant product) {
        inventory.put(product.getProductVariantId(), product);
    }

    public void remove(ProductVariant product) {
        if (!inventory.containsKey(product.getProductVariantId())) {
            throw new InventoryException("Product not in Inventory");
        }
        inventory.remove(product.getProductVariantId());
    }

    public String tryOrdering(ShoppingBasket shoppingBasket) {
        return shoppingBasket.getShoppingBasket().stream()
            .map(buyProduct -> {
                ProductVariant product = inventory.get(buyProduct.getVariantId());
                if (product == null) {
                    return "재고 정보 없음: " + buyProduct;
                } else if (product.isNotAbleToOrder(buyProduct.getQuantity())) {
                    return "재고 부족함: " + buyProduct;
                } else {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.joining("\n"));
    }

    public void purchase(ShoppingBasket shoppingBasket) {
        shoppingBasket.getShoppingBasket()
            .forEach(buyProduct -> {
                ProductVariant inventoryProduct = inventory.get(buyProduct.getVariantId());
                inventoryProduct.purchase(buyProduct.getQuantity());
            });
    }

}
