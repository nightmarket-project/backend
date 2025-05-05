package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionCombinationIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemOptionCombinationId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();
        ItemOptionCombinationId ItemOptionCombinationId1 = new ItemOptionCombinationId(id);
        ItemOptionCombinationId ItemOptionCombinationId2 = new ItemOptionCombinationId(id);

        assertThat(ItemOptionCombinationId1).isEqualTo(ItemOptionCombinationId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한ItemOptionCombinationId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ItemOptionCombinationId ItemOptionCombinationId1 = new ItemOptionCombinationId(id1);
        ItemOptionCombinationId ItemOptionCombinationId2 = new ItemOptionCombinationId(id2);

        assertThat(ItemOptionCombinationId1).isNotEqualTo(ItemOptionCombinationId2);
    }

}
