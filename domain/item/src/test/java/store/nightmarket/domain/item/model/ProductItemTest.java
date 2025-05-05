package store.nightmarket.domain.item.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.ProductItemException;
import store.nightmarket.itemcore.model.ItemDetailOption;
import store.nightmarket.itemcore.model.ItemOptionGroup;
import store.nightmarket.itemcore.model.ItemOption;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductItemTest {

    @Test
    @DisplayName("ProductItem 객체가 생성이 되는지 테스트")
    void shouldCreateProductItemSuccessfully() {
        ProductItem productItem = newProductItem();

        assertThat(productItem).isNotNull();
        assertThat(productItem).isInstanceOf(ProductItem.class);
    }

    @Test
    @DisplayName("ProductItem 어떤 옵션이 구매 불가능하다면 예외 발생")
    void shouldThrowExceptionWhenAnyProductItemOptionIsNotAvailableToBuy() {
        ProductItem productItem = newProductItemWithZeroQuantity();
        ProductItem otherProductItem = newProductItem();
        assertThatThrownBy(() -> productItem.isAvailableToBuy(otherProductItem)).isInstanceOf(ProductItemException.class);
    }

    private ProductItem newProductItem() {
        return ProductItem.newInstance(
                new ItemId(UUID.randomUUID()),
                new Name("user"),
                newBasicOption(),
                newAdditionOption(),
                new UserId(UUID.randomUUID())
        );
    }

    private ProductItem newProductItemWithZeroQuantity() {
        return ProductItem.newInstance(
                new ItemId(UUID.randomUUID()),
                new Name("user"),
                newBasicOption(),
                newAdditionOptionWithZeroQuantity(),
                new UserId(UUID.randomUUID())
        );
    }

    private ItemOptionGroup newBasicOption() {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(UUID.randomUUID()),
                new ItemId(UUID.randomUUID()),
                List.of(
                        ItemOption.newInstance(
                                new ItemOptionId(UUID.randomUUID()),
                                new Name("색상"),
                                colorOptionList()),
                        ItemOption.newInstance(
                                new ItemOptionId(UUID.randomUUID()),
                                new Name("사이즈"),
                                sizeOptionList()
                        )
                )
        );
    }

    private ItemOption newAdditionOption() {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()),
                new Name("추가 상품"),
                List.of(
                        ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                                new Name("양말"),
                                new Price(BigDecimal.valueOf(5000)),
                                new Quantity(BigDecimal.valueOf(10))
                        ),
                        ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                                new Name("모자"),
                                new Price(BigDecimal.valueOf(6000)),
                                new Quantity(BigDecimal.valueOf(10))
                        )
                )
        );
    }

    private ItemOption newAdditionOptionWithZeroQuantity() {
        return ItemOption.newInstance(
                new ItemOptionId(UUID.randomUUID()),
                new Name("추가 상품"),
                List.of(
                        ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                                new Name("양말"),
                                new Price(BigDecimal.valueOf(5000)),
                                new Quantity(BigDecimal.valueOf(10))
                        ),
                        ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                                new Name("모자"),
                                new Price(BigDecimal.valueOf(6000)),
                                new Quantity(BigDecimal.ZERO)
                        )
                )
        );
    }

    private List<ItemDetailOption> colorOptionList() {
        return List.of(
                ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                        new Name("블랙"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(10))
                ),
                ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                        new Name("화이트"),
                        new Price(BigDecimal.valueOf(2000)),
                        new Quantity(BigDecimal.valueOf(20))
                )
        );
    }

    private List<ItemDetailOption> sizeOptionList() {
        return List.of(
                ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                        new Name("100"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(10))
                ),
                ItemDetailOption.newInstance(new ItemDetailOptionId(UUID.randomUUID()),
                        new Name("105"),
                        new Price(BigDecimal.valueOf(1000)),
                        new Quantity(BigDecimal.valueOf(20))
                )
        );
    }

}
