package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.common.util.ItemOptionValidationError;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ProductItem extends BaseModel<ItemId> {

    private Name name;
    private ItemOptionGroup basicOption;
    private ItemOption additionalOption;
    private UserId seller;


    private ProductItem(
            ItemId id,
            Name name,
            ItemOptionGroup basicOption,
            ItemOption additionalOption,
            UserId seller
    ) {
        super(id);
        this.name = name;
        this.basicOption = basicOption;
        this.additionalOption = additionalOption;
        this.seller = seller;
    }

    public static ProductItem newInstance(
            ItemId id,
            Name name,
            ItemOptionGroup basicOption,
            ItemOption additionalOption,
            UserId seller
    ) {
        return new ProductItem(
                id,
                name,
                basicOption,
                additionalOption,
                seller
        );
    }

    public ItemId getItemId() {
        return internalId();
    }

    public List<ItemOptionValidationError> findProductItemErrors(UserBuyProductItem buyProductItem) {
        List<ItemOptionValidationError> findErrors = new ArrayList<>();
        findErrors.addAll(basicOption.findOptionGroupErrors(buyProductItem.getBasicOption()));
        findErrors.addAll(additionalOption.findOptionErrors(buyProductItem.getAdditionalOption()));

        return findErrors;
    }

    public void reduceProductBy(UserBuyProductItem buyProductItem) {
        basicOption.reduceOptionGroupQuantityBy(buyProductItem.getBasicOption());
        additionalOption.reduceOptionQuantityBy(buyProductItem.getAdditionalOption());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProductItem other = (ProductItem) obj;
        return Objects.equals(getItemId(), other.getItemId())
                && Objects.equals(name, other.name)
                && Objects.equals(basicOption, other.basicOption)
                && Objects.equals(additionalOption, other.additionalOption)
                && Objects.equals(seller, other.seller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), name, basicOption, additionalOption, seller);
    }

}
