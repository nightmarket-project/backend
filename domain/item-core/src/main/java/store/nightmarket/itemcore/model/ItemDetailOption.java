package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.util.Optional;

public class ItemDetailOption extends BaseModel<ItemDetailOptionId> {

    private Name name;
    private Price price;
    private Quantity quantity;

    private ItemDetailOption(
            ItemDetailOptionId id,
            Name name,
            Price price,
            Quantity quantity
    ) {
        super(id);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static ItemDetailOption newInstance(
            ItemDetailOptionId id,
            Name name,
            Price price,
            Quantity quantity
    ) {
        return new ItemDetailOption(id, name, price, quantity);
    }

    private ItemDetailOptionId getDetailOptionId() {
        return internalId();
    }

    public Optional<UserItemDetailOption> isAvailableToBuy(UserItemDetailOption buyOption) {
        if(!this.getDetailOptionId().equals(buyOption.getDetailOptionId())) {
            return Optional.empty();
        }

        updatePurchasableStatus(buyOption);

        return Optional.of(buyOption);
    }

    private void updatePurchasableStatus(UserItemDetailOption buyOption) {
        if (!quantity.isGreaterThanOrEqualTo(buyOption.getQuantity())) {
            buyOption.markAsNotPurchasable();
            return;
        }
        buyOption.markAsPurchasable();
    }

}
