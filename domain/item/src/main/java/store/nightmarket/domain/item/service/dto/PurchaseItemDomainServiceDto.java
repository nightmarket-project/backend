package store.nightmarket.domain.item.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.item.model.ItemGroup;
import store.nightmarket.itemcore.model.Cart;

public class PurchaseItemDomainServiceDto {

  @Getter
  @Builder
  public static class Input {

    private final ItemGroup itemGroup;
    private final Cart cart;

  }

  @Getter
  @Builder
  public static class Event {

    private final Cart cart;

  }

}
