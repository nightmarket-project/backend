package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.ItemOptionCombination;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.*;

import java.util.List;

public class ProductItem extends BaseModel<ItemId> {

    private Name name;
    private ItemOptionCombination basicOption;
    private ItemOptionGroup AdditionalOption;
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
        AdditionalOption = additionalOption;
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

    public void requestPurchase(
            List<ItemOption> options
    ) {

    }
}