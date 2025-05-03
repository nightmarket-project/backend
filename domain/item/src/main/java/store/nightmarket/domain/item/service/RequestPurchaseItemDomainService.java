package store.nightmarket.domain.item.service;

import org.springframework.stereotype.Component;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.item.model.ProductItem;

import static store.nightmarket.domain.item.service.dto.RequestPurchaseItemDomainServiceDto.*;

@Component
public class RequestPurchaseItemDomainService
        implements BaseDomainService<Input, Event> {
    @Override
    public Event execute(Input input) {
        ProductItem productItem = input.getProductItem();
        ProductItem buyProductITem = input.getBuyProductItem();
        productItem.isAvailableToBuy(buyProductITem);

        return Event.builder()
                .productItem(buyProductITem)
                .build();
    }
}
