package store.nightmarket.itemcore.model;

import lombok.Getter;
import store.nightmarket.itemcore.state.OptionState;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

@Getter
public class ItemOption {

    private Name name;
    private Price price;
    private Quantity quantity;
    private OptionState state;

    private ItemOption(Name name, Price price, Quantity quantity, OptionState state) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.state = state;
    }

    public static ItemOption create(Name name, Price price, Quantity quantity, OptionState state) {
        return new ItemOption(name, price, quantity, state);
    }

}
