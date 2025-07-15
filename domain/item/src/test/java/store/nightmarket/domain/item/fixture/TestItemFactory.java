package store.nightmarket.domain.item.fixture;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.domain.item.valueobject.VariantOptionValueId;

public class TestItemFactory {

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
        OptionGroup optionGroup,
        OptionValue optionValue
    ) {
        return VariantOptionValue.newInstance(
            new VariantOptionValueId(variantOptionValueId),
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
        String value,
        int price,
        int order
    ) {
        return OptionValue.newInstance(
            new OptionValueId(optionValueId),
            value,
            new Price(BigDecimal.valueOf(price)),
            order
        );
    }

}
