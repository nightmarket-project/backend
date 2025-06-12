package store.nightmarket.itemweb.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.itemcore.model.Item;
import store.nightmarket.itemcore.model.Cart;
import store.nightmarket.itemcore.model.UserItem;

public class AddProductToBasketItemWebDomainServiceDto {

  @Getter
  @Builder
  public static class Input {

    private final Item item;
    private final UserItem userBuyItem;
    private final Cart cart;
  }

  @Getter
  @Builder
  public static class Event {

    private final Cart cart;
  }

}
