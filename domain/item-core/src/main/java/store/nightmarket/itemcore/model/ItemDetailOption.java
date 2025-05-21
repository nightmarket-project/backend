package store.nightmarket.itemcore.model;

import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.itemcore.exception.ErrorResult;
import store.nightmarket.itemcore.valueobject.ItemDetailOptionId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.Price;
import store.nightmarket.itemcore.valueobject.Quantity;

import java.util.Objects;
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

    public ItemDetailOptionId getDetailOptionId() {
        return internalId();
    }

    // 상세 수량 수량 차감
    public void reduceDetailOptionQuantityBy(UserItemDetailOption buyDetailOption) {
        quantity = quantity.subtract(buyDetailOption.getQuantity());
    }

    public Optional<ErrorResult> findDetailOptionError(UserItemDetailOption buyDetailOption) {
        if (quantity.isLessThan(buyDetailOption.getQuantity())) {
            return Optional.of(new ErrorResult(getDetailOptionId(), "보유 수량이 요청 수량보다 작다."));
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemDetailOption other = (ItemDetailOption) obj;
        return Objects.equals(getDetailOptionId(), other.getDetailOptionId())
                && Objects.equals(name, other.name)
                && Objects.equals(price, other.price)
                && Objects.equals(quantity, other.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetailOptionId(),name, price, quantity);
    }

}
