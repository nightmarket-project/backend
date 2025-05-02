package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.state.OptionState;
import store.nightmarket.itemcore.valueobject.ItemOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

@Getter
public class ItemOption extends BaseModel<ItemOptionId> {

    private Name name;
    private Price price;
    private Quantity quantity;
    private OptionState state;

    private ItemOption(
            ItemOptionId id,
            Name name,
            Price price,
            Quantity quantity,
            OptionState state
    ) {
        super(id);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
    }

    public static ItemOption newInstance(
            ItemOptionId id,
            Name name,
            Price price,
            Quantity quantity,
            OptionState state
    ) {
        return new ItemOption(id, name, price, quantity, state);
    }

}
