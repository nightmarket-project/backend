package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemOptionIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemOptionId equals 비교에서 같다")
    void ItemOptionIdEqualsTest() {
        UUID id = UUID.randomUUID();
        ItemOptionId itemOptionId1 = new ItemOptionId(id);
        ItemOptionId itemOptionId2 = new ItemOptionId(id);

        assertThat(itemOptionId1).isEqualTo(itemOptionId2);

    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemOptionId equals 비교에서 다르다")
    void ItemIdNotEqualTest() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ItemOptionId itemOptionId1 = new ItemOptionId(id1);
        ItemOptionId itemOptionId2 = new ItemOptionId(id2);

        assertThat(itemOptionId1).isNotEqualTo(itemOptionId2);
    }
}