package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.model.UserItemOption;
import store.nightmarket.itemcore.model.UserItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemId;

@Getter
public class UserProductItem extends BaseModel<ItemId> {

    private UserItemOptionGroup basicOption;
    private UserItemOption additionalOption;

    private UserProductItem(
            ItemId id,
            UserItemOptionGroup basicOption,
            UserItemOption additionalOption
    ) {
        super(id);
        this.basicOption = basicOption;
        this.additionalOption = additionalOption;
    }

    public static UserProductItem newInstance(
            ItemId itemId,
            UserItemOptionGroup basicOption,
            UserItemOption additionalOption
    ) {
        return new UserProductItem(
                itemId,
                basicOption,
                additionalOption
        );
    }

    public ItemId getItemId() {
        return internalId();
    }

}
