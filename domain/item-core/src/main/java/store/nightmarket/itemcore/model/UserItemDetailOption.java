package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.Quantity;

@Getter
public class UserItemDetailOption extends BaseModel<ItemDetailOptionId> {

    private Quantity quantity;
    private boolean isPurchasable;

    private UserItemDetailOption(
            ItemDetailOptionId detailOptionId,
            Quantity quantity
    ) {
        super(detailOptionId);
        this.quantity = quantity;
        isPurchasable = false;
    }

    public static UserItemDetailOption newInstance(
            ItemDetailOptionId detailOptionId,
            Quantity quantity
    ) {
        return new UserItemDetailOption(detailOptionId, quantity);
    }

    public ItemDetailOptionId getDetailOptionId() {
        return internalId();
    }

    public void markAsPurchasable() {
        isPurchasable = true;
    }

    public void markAsNotPurchasable() {
        isPurchasable = false;
    }

}
