package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

public class ItemOption extends BaseModel<ItemOptionId> {

    private Name name;
    private Price price;
    private Quantity quantity;

    private ItemOption(
            ItemOptionId id,
            Name name,
            Price price,
            Quantity quantity
    ) {
        super(id);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static ItemOption newInstance(
            ItemOptionId id,
            Name name,
            Price price,
            Quantity quantity
    ) {
        return new ItemOption(id, name, price, quantity);
    }

    public boolean isAvailableToBuy() {
        return !quantity.isZero();
    }
}
