package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.Quantity;

public class UserItemDetailOption extends BaseModel<ItemDetailOptionId> {

    private Quantity quantity;

    private UserItemDetailOption(
            ItemDetailOptionId detailOptionId,
            Quantity quantity
    ) {
        super(detailOptionId);
        this.quantity = quantity;
    }

    public static UserItemDetailOption newInstance(
            ItemDetailOptionId detailOptionId,
            Quantity quantity
    ) {
        return new UserItemDetailOption(detailOptionId, quantity);
    }

}
