package store.nightmarket.domain.item.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.model.UserItemOption;
import store.nightmarket.itemcore.model.UserItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.UserId;

import java.util.Collections;
import java.util.Optional;

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

    private ItemId getItemId() {
        return internalId();
    }

    public Optional<UserProductItem> isAvailableToBuy(UserProductItem buyUserItem) {
        if (!getItemId().equals(buyUserItem.getItemId())) {
            return Optional.empty();
        }

        PurchasableOptionResult buyResult = getAvailableToBuy(buyUserItem);

        return Optional.of(UserProductItem.newInstance(
                buyUserItem.getItemId(),
                buyResult.buyBasicOption(),
                buyResult.buyAdditionalOption()));
    }

    private PurchasableOptionResult getAvailableToBuy(UserProductItem buyUserItem) {
        UserItemOptionGroup buyBasicOption = basicOption.isAvailableToBuy(buyUserItem.getBasicOption());

        UserItemOption userAdditionalOption = buyUserItem.getAdditionalOption();
        UserItemOption buyAdditionalOption = additionalOption.isAvailableToBuy(userAdditionalOption)
                .orElseGet(() -> UserItemOption.newInstance(
                        userAdditionalOption.getOptionId(),
                        Collections.EMPTY_LIST));
        return new PurchasableOptionResult(buyBasicOption, buyAdditionalOption);
    }

    private record PurchasableOptionResult(UserItemOptionGroup buyBasicOption, UserItemOption buyAdditionalOption) {
    }

}
