package store.nightmarket.itemcore.model;

import java.util.ArrayList;
import java.util.List;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductId;

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
