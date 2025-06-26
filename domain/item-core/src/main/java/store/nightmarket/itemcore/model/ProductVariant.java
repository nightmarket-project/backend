package store.nightmarket.itemcore.model;

import java.util.ArrayList;
import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ProductId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;

public class ProductVariant extends BaseModel<ProductVariantId> {

    private ProductId productId;
    private String SKUCode;
    private List<VariantOptionValue> variantOptionValueList;

    private ProductVariant(
        ProductVariantId id,
        ProductId productId,
        String SKUCode,
        List<VariantOptionValue> variantOptionValueList
    ) {
        super(id);
        this.productId = productId;
        this.SKUCode = SKUCode;
        this.variantOptionValueList = variantOptionValueList;
    }

    public ProductVariant newInstance(
        ProductVariantId id,
        ProductId productId,
        String SKUCode,
        List<VariantOptionValue> variantOptionValueList
    ) {
        return new ProductVariant(
            id,
            productId,
            SKUCode,
            variantOptionValueList
        );
    }

}
