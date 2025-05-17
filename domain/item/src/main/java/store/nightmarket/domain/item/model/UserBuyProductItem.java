package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.model.UserItemOption;
import store.nightmarket.itemcore.model.UserItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemId;

@Getter
public class UserBuyProductItem extends BaseModel<ItemId> {

    private UserItemOptionGroup basicOption;
    private UserItemOption additionalOption;

    private UserBuyProductItem(
            ItemId id,
            UserItemOptionGroup basicOption,
            UserItemOption additionalOption
    ) {
        super(id);
        this.basicOption = basicOption;
        this.additionalOption = additionalOption;
    }

    public static UserBuyProductItem newInstance(
            ItemId itemId,
            UserItemOptionGroup basicOption,
            UserItemOption additionalOption
    ) {
        return new UserBuyProductItem(
                itemId,
                basicOption,
                additionalOption
        );
    }

    public ItemId getItemId() {
        return internalId();
    }

}
