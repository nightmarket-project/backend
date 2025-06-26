package store.nightmarket.itemcore.fixture;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import store.nightmarket.itemcore.model.Inventory;
import store.nightmarket.itemcore.model.OptionGroup;
import store.nightmarket.itemcore.model.OptionValue;
import store.nightmarket.itemcore.model.ProductVariant;
import store.nightmarket.itemcore.model.VariantOptionValue;
import store.nightmarket.itemcore.valueobject.InventoryId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.OptionGroupId;
import store.nightmarket.itemcore.valueobject.OptionValueId;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemcore.valueobject.VariantOptionValueId;

public class TestItemFactory {

    public static Inventory createInventory(
        UUID inventoryId,
        List<ProductVariant> inventory
    ) {
        return Inventory.newInstance(
            new InventoryId(inventoryId),
            inventory
        );
    }

    public static ProductVariant createProductVariant(
        UUID productVariantId,
        UUID productId,
        UUID sellerId,
        String SKUId,
        int quantity,
        List<VariantOptionValue> variantOptionValueList
    ) {
        return ProductVariant.newInstance(
            new ProductVariantId(productVariantId),
            new ProductId(productId),
            new UserId(sellerId),
            SKUId,
            new Quantity(BigDecimal.valueOf(quantity)),
            variantOptionValueList
        );
    }

    public static VariantOptionValue createVariantOptionValue(
        UUID variantOptionValueId,
        UUID ProductVariantId,
        OptionGroup optionGroup,
        OptionValue optionValue
    ) {
        return VariantOptionValue.newInstance(
            new VariantOptionValueId(variantOptionValueId),
            new ProductVariantId(ProductVariantId),
            optionGroup,
            optionValue
        );
    }

    public static OptionGroup createOptionGroup(
        UUID optionGroupId,
        UUID productId,
        String name,
        int order,
        List<OptionValue> optionValueList
    ) {
        return OptionGroup.newInstance(
          new OptionGroupId(optionGroupId),
          new ProductId(productId),
          new Name(name),
          order,
          optionValueList
        );
    }

    public static OptionValue createOptionValue(
        UUID optionValueId,
        UUID optionGroupId,
        String value,
        int price,
        int order
    ) {
        return OptionValue.newInstance(
            new OptionValueId(optionValueId),
            new OptionGroupId(optionGroupId),
            value,
            new Price(BigDecimal.valueOf(price)),
            order
        );
    }

}
