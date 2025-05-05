package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemDetailOptionIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemDetailOptionId equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();
        ItemDetailOptionId itemDetailOptionId1 = new ItemDetailOptionId(id);
        ItemDetailOptionId itemDetailOptionId2 = new ItemDetailOptionId(id);

        assertThat(itemDetailOptionId1).isEqualTo(itemDetailOptionId2);

    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemDetailOptionId equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ItemDetailOptionId itemDetailOptionId1 = new ItemDetailOptionId(id1);
        ItemDetailOptionId itemDetailOptionId2 = new ItemDetailOptionId(id2);

        assertThat(itemDetailOptionId1).isNotEqualTo(itemDetailOptionId2);
    }

}
