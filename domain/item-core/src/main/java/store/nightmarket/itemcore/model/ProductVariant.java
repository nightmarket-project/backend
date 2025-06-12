package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductId;
import store.nightmarket.itemcore.valueobject.ProductVariantId;

public class ProductVariant extends BaseModel<ProductVariantId> {

    private ProductId productId;
    private Price price;

    private ProductVariant(
        ProductVariantId id,
        ProductId productId,
        Price price
    ) {
        super(id);
        this.productId = productId;
        this.price = price;
    }

    public ProductVariant newInstance(
        ProductVariantId id,
        ProductId productId,
        Price price
    ) {
        return new ProductVariant(
            id,
            productId,
            price
        );
    }

}
