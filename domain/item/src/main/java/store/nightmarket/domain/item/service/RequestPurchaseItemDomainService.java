package store.nightmarket.domain.item.service;

import org.springframework.stereotype.Component;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserProductItem;

import static store.nightmarket.domain.item.service.dto.RequestPurchaseItemDomainServiceDto.*;

@Component
public class RequestPurchaseItemDomainService
        implements BaseDomainService<Input, Event> {
    @Override
    public Event execute(Input input) {
        ProductItem productItem = input.getProductItem();
        UserProductItem buyProductITem = input.getBuyProductItem();

        UserProductItem userProductItem = productItem.isAvailableToBuy(buyProductITem)
                .orElseThrow(() -> new ProductItemException("아이템과 구매 아이템이 다릅니다."));

        return Event.builder()
                .buyProductItem(userProductItem)
                .build();
    }
}
