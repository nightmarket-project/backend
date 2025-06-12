package store.nightmarket.domain.item.service;

import static store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Event;
import static store.nightmarket.domain.item.service.dto.PurchaseItemDomainServiceDto.Input;

import java.util.List;
import org.springframework.stereotype.Component;
import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.common.util.ItemOptionValidationError;
import store.nightmarket.domain.item.model.ItemGroup;
import store.nightmarket.itemcore.model.Cart;

@Component
public class PurchaseItemDomainService
    implements BaseDomainService<Input, Event> {

  @Override
  public Event execute(Input input) {
    ItemGroup itemGroup = input.getItemGroup();
    Cart cart = input.getCart();

    List<ItemOptionValidationError> productErrors = itemGroup.findQuantityErrors(cart);


  }

//    @Override
//    public Event execute(Input input) {
//        ItemGroup itemGroup = input.getItemGroup();
//        UserBuyItemGroup buyProductITem = input.getBuyProductItem();
//
//        if (!itemGroup.getItemId().equals(buyProductITem.getItemId())) {
//            throw new ProductItemException("Product item id does not match buy product item id");
//        }
//
//        List<ItemOptionValidationError> productItemErrors = itemGroup.findProductItemErrors(buyProductITem);
//        if (!productItemErrors.isEmpty()) {
//            throw new ProductItemException(buyProductITem.toString());
//        }
//        itemGroup.reduceProductBy(buyProductITem);
//
//
//        return Event.builder()
//                .buyProductItem(buyProductITem)
//                .build();
//    }

}
