package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.itemcore.model.ItemOptionCombination;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.*;

public class ProductItem extends BaseModel<ItemId> {

    private Name name;
    private ItemOptionCombination basicOption;
    private ItemOptionGroup additionalOption;
    private RegistrantId registrantId;


    private ProductItem(
            ItemId id,
            Name name,
            ItemOptionCombination basicOption,
            ItemOptionGroup additionalOption,
            RegistrantId registrantId
    ) {
        super(id);
        this.name = name;
        this.basicOption = basicOption;
        this.additionalOption = additionalOption;
        this.registrantId = registrantId;
    }

    public static ProductItem newInstance(
            ItemId id,
            Name name,
            ItemOptionCombination basicOption,
            ItemOptionGroup additionalOption,
            RegistrantId registrantId
    ) {
        return new ProductItem(
                id,
                name,
                basicOption,
                additionalOption,
                registrantId
        );
    }

    public void requestPurchase() {
        if(!basicOption.isAvailableToBuy()
                || !additionalOption.isAvailableToBuy()) {
            throw new ProductItemException("You cannot place a purchase request because there is no purchase quantity.");
        }
    }
}