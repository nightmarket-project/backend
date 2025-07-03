package store.nightmarket.domain.item.service.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.ShoppingBasket;

public class PurchaseManyProductItemDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        List<ProductVariant> purchaseProductList;
        ShoppingBasket shoppingBasket;

    }


    @Getter
    @Builder
    public static class Event {

        ShoppingBasket shoppingBasket;

    }

}
