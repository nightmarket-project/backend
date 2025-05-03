package store.nightmarket.domain.item.model;

import lombok.Getter;
import store.nightmarket.common.domain.model.BaseModel;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.itemcore.model.ItemOptionCombination;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.*;

@Getter
public class ProductItem extends BaseModel<ItemId> {

    private Name name;
    private ItemOptionCombination basicOption;
    private ItemOptionGroup additionalOption;
    private RegistrantId registrantId;


    private ProductItem(
            ItemId id,
            Name name,
            ItemOptionCombination basicOption,
            ItemOptionGroup additionalOption,
            RegistrantId registrantId
    ) {
        super(id);
        this.name = name;
        this.basicOption = basicOption;
        this.additionalOption = additionalOption;
        this.registrantId = registrantId;
    }

    public static ProductItem newInstance(
            ItemId id,
            Name name,
            ItemOptionCombination basicOption,
            ItemOptionGroup additionalOption,
            RegistrantId registrantId
    ) {
        return new ProductItem(
                id,
                name,
                basicOption,
                additionalOption,
                registrantId
        );
    }

    public void isAvailableToBuy(ProductItem buyItem) {
        try{
            basicOption.isAvailableToBuy(buyItem.getBasicOption());
        } catch (RuntimeException e){
            throw new ProductItemException("기본 옵션 수량 부족 에러 발생.");
        }
        try{
            additionalOption.isAvailableToBuy(buyItem.getAdditionalOption());
        } catch (RuntimeException e){
            throw new ProductItemException("추가 옵션 수량 부족 에러 발생.");
        }
    }
}