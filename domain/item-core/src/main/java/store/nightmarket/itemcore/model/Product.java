package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.ProductId;

public class Product extends BaseModel<ProductId> {

    private Name name;
    private String description;
    private Price price;

    private Product(
        ProductId id,
        Name name,
        String description,
        Price price
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product newInstance(
        ProductId id,
        Name name,
        String description,
        Price price
    ) {
        return new Product(
            id,
            name,
            description,
            price
        );
    }

}
