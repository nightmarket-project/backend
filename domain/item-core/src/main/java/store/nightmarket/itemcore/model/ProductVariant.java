package store.nightmarket.itemcore.model;

import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ProductId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;
import store.nightmarket.itemcore.valueobject.Quantity;
import store.nightmarket.itemcore.valueobject.UserId;

public class ProductVariant extends BaseModel<ProductVariantId> {

    private ProductId productId;
    private UserId seller;
    private String SKUCode;
    private Quantity quantity;
    private List<VariantOptionValue> variantOptionValueList;

    private ProductVariant(
        ProductVariantId id,
        ProductId productId,
        UserId seller,
        String SKUCode,
        Quantity quantity,
        List<VariantOptionValue> variantOptionValueList
    ) {
        super(id);
        this.productId = productId;
        this.seller = seller;
        this.SKUCode = SKUCode;
        this.quantity = quantity;
        this.variantOptionValueList = variantOptionValueList;
    }

    public ProductVariant newInstance(
        ProductVariantId id,
        ProductId productId,
        UserId seller,
        String SKUCode,
        Quantity quantity,
        List<VariantOptionValue> variantOptionValueList
    ) {
        return new ProductVariant(
            id,
            productId,
            seller,
            SKUCode,
            quantity,
            variantOptionValueList
        );
    }

}
