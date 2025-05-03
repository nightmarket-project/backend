package store.nightmarket.domain.item.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.domain.item.model.ProductItem;
import store.nightmarket.itemcore.model.ItemOptionCombination;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemcore.valueobject.Name;
import store.nightmarket.itemcore.valueobject.RegistrantId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static store.nightmarket.domain.item.service.dto.RequestPurchaseItemDomainServiceDto.Event;
import static store.nightmarket.domain.item.service.dto.RequestPurchaseItemDomainServiceDto.Input;


class RequestPurchaseItemDomainServiceTest {

    private final RequestPurchaseItemDomainService service = new RequestPurchaseItemDomainService();

    @Test
    @DisplayName("정상적인 구매 요청 시 Event를 반환한다.")
    void requestPurchaseItemSuccess() {

        ItemOptionCombination mockBasicOption = mock(ItemOptionCombination.class);
        ItemOptionGroup mockAdditionalOption = mock(ItemOptionGroup.class);

        when(mockBasicOption.isAvailableToBuy()).thenReturn(true);
        when(mockAdditionalOption.isAvailableToBuy()).thenReturn(true);

        ProductItem productItem = ProductItem.newInstance(
                new ItemId(UUID.randomUUID()),
                new Name("테스트상품"),
                mockBasicOption,
                mockAdditionalOption,
                new RegistrantId(UUID.randomUUID())
        );

        Input input = Input.builder()
                .productItem(productItem).build();

        Event event = service.execute(input);
        assertThat(event).isNotNull();
        assertThat(event.getProductItem()).isEqualTo(productItem);
    }

    @Test
    @DisplayName("구매 수량이 부족할 경우 예외가 발생합니다.")
    void requestPurchaseItemFailure() {

        ItemOptionCombination mockBasicOption = mock(ItemOptionCombination.class);
        ItemOptionGroup mockAdditionalOption = mock(ItemOptionGroup.class);

        when(mockBasicOption.isAvailableToBuy()).thenReturn(true);
        when(mockAdditionalOption.isAvailableToBuy()).thenReturn(false);

        ProductItem productItem = ProductItem.newInstance(
                new ItemId(UUID.randomUUID()),
                new Name("item-2"),
                mockBasicOption,
                mockAdditionalOption,
                new RegistrantId(UUID.randomUUID())
        );

        Input input = Input.builder()
                .productItem(productItem)
                .build();

        assertThatThrownBy(() -> service.execute(input))
                .isInstanceOf(ProductItemException.class);
    }
}