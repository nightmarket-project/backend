package store.nightmarket.domain.item.model;

import java.util.List;
import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;
import store.nightmarket.domain.item.valueobject.UserId;

@Getter
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

    public static ProductVariant newInstance(
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

    public ProductVariantId getProductVariantId() {
        return internalId();
    }

    public boolean isNotAbleToOrder(Quantity buyQuantity) {
        return quantity.isLessThan(buyQuantity);
    }

    public void purchase(Quantity buyQuantity) {
        quantity = quantity.subtract(buyQuantity);
    }


}
