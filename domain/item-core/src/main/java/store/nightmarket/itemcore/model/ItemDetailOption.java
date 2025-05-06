package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ItemOptionException;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

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

    public void isAvailableToBuy(ItemDetailOption buyOption) {
        if (!quantity.isGreaterThanOrEqualTo(buyOption.quantity)) {
            throw new ItemOptionException("해당 옵션은 수량 부족으로 구매 불가합니다. 옵션 이름: " + name.getValue());
        }
    }

}
