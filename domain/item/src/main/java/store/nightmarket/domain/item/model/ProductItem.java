package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.model.ItemOptionCombination;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.*;

public class ProductItem extends BaseModel<ItemId> {

    private Name name;
    private Price price;
    private Quantity quantity;
    private ItemOptionCombination basicOption;
    private ItemOptionGroup AdditionalOption;
    private RegistrantId registrantId;


    private ProductItem(
            ItemId id,
            Name name,
            Price price,
            Quantity quantity,
            ItemOptionCombination basicOption,
            ItemOptionGroup additionalOption,
            RegistrantId registrantId
    ) {
        super(id);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.basicOption = basicOption;
        AdditionalOption = additionalOption;
        this.registrantId = registrantId;
    }

    public static ProductItem newInstance(
            ItemId id,
            Name name,
            Price price,
            Quantity quantity,
            ItemOptionCombination basicOption,
            ItemOptionGroup additionalOption,
            RegistrantId registrantId
    ) {
        return new ProductItem(
                id,
                name,
                price,
                quantity,
                basicOption,
                additionalOption,
                registrantId
        );
    }
}