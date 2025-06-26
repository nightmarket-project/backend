package store.nightmarket.domain.item.model;

import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;
import store.nightmarket.domain.item.valueobject.ProductId;

public class Product extends BaseModel<ProductId> {

    private Name name;
    private String description;
    private Price price;
    private List<OptionGroup> optionGroupList;
    private List<ProductVariant> productVariantList;

    private Product(
        ProductId id,
        Name name,
        String description,
        Price price,
        List<OptionGroup> optionGroupList,
        List<ProductVariant> productVariantList
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.optionGroupList = optionGroupList;
        this.productVariantList = productVariantList;
    }

    public static Product newInstance(
        ProductId id,
        Name name,
        String description,
        Price price,
        List<OptionGroup> optionGroupList,
        List<ProductVariant> productVariantList
    ) {
        return new Product(
            id,
            name,
            description,
            price,
            optionGroupList,
            productVariantList
        );
    }

}
