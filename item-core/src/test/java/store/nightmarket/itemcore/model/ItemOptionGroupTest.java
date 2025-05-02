package store.nightmarket.itemcore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemOptionGroupTest {

    private final Name NAME = new Name("색상");
    private final List<ItemOption> ITEM_OPTIONS = List.of(
            ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                    new Name("블랙"),
                    new Price(BigDecimal.valueOf(1000)),
                    new Quantity(BigDecimal.valueOf(10))
            ),
            ItemOption.newInstance(new ItemOptionId(UUID.randomUUID()),
                    new Name("화이트"),
                    new Price(BigDecimal.valueOf(2000)),
                    new Quantity(BigDecimal.valueOf(20))
            )
    );

    @Test
    @DisplayName("DetailItemOption은 id, 이름, 종류로 생성된다")
    void newInstanceDetailItemOptionSuccessfully() {
        ItemOptionGroup option = newInstanceDetailItemOption();

        assertThat(option).isNotNull();
        assertThat(option).isInstanceOf(ItemOptionGroup.class);
    }

    private ItemOptionGroup newInstanceDetailItemOption() {
        return ItemOptionGroup.newInstance(
                new ItemOptionGroupId(UUID.randomUUID()), NAME, ITEM_OPTIONS
        );
    }
}