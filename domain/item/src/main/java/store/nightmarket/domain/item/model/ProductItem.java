package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class ProductItem extends BaseModel<ItemId> {

    private Name name;
    private ItemOptionGroup basicOption;
    private ItemOption additionalOption;
    private UserId seller;


    private ProductItem(
            ItemId id,
            Name name,
            ItemOptionGroup basicOption,
            ItemOption additionalOption,
            UserId seller
    ) {
        super(id);
        this.name = name;
        this.basicOption = basicOption;
        this.additionalOption = additionalOption;
        this.seller = seller;
    }

    public static ProductItem newInstance(
            ItemId id,
            Name name,
            ItemOptionGroup basicOption,
            ItemOption additionalOption,
            UserId seller
    ) {
        return new ProductItem(
                id,
                name,
                basicOption,
                additionalOption,
                seller
        );
    }

    public ItemId getItemId() {
        return internalId();
    }

    public List<ErrorResult> findProductItemErrors(UserBuyProductItem buyProductItem) {
        List<ErrorResult> findErrors = new ArrayList<>();
        findErrors.addAll(basicOption.findOptionGroupErrors(buyProductItem.getBasicOption()));
        findErrors.addAll(additionalOption.findOptionErrors(buyProductItem.getAdditionalOption()));

        return findErrors;
    }

    public void reduceProductBy(UserBuyProductItem buyProductItem) {
        basicOption.reduceOptionGroupQuantityBy(buyProductItem.getBasicOption());
        additionalOption.reduceOptionQuantityBy(buyProductItem.getAdditionalOption());
    }

}
