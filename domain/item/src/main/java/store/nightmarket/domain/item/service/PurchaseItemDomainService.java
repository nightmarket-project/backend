package store.nightmarket.domain.item.service;

import org.springframework.stereotype.Component;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.common.util.ItemOptionValidationError;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.domain.item.model.UserBuyProductItem;

import java.util.List;

import static store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import static store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;

@Component
public class PurchaseItemDomainService
        implements BaseDomainService<Input, Event> {

    @Override
    public Event execute(Input input) {
        ProductItem productItem = input.getProductItem();
        UserBuyProductItem buyProductITem = input.getBuyProductItem();

        if (!productItem.getItemId().equals(buyProductITem.getItemId())) {
            throw new ProductItemException("Product item id does not match buy product item id");
        }

        productItem.findProductItemErrors(buyProductITem)
                .ifPresentOrElse(
                        errors -> {
                            throw new ProductItemException(errors.toString());
                        },
                        () -> productItem.reduceProductBy(buyProductITem)
                );


        return Event.builder()
                .buyProductItem(buyProductITem)
                .build();
    }

}
