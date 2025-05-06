package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemOptionGroupIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemOptionGroupId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();
        ItemOptionGroupId itemOptionGroupId1 = new ItemOptionGroupId(id);
        ItemOptionGroupId itemOptionGroupId2 = new ItemOptionGroupId(id);

        assertThat(itemOptionGroupId1).isEqualTo(itemOptionGroupId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemOptionGroupId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        ItemOptionGroupId itemOptionGroupId1 = new ItemOptionGroupId(id1);
        ItemOptionGroupId itemOptionGroupId2 = new ItemOptionGroupId(id2);

        assertThat(itemOptionGroupId1).isNotEqualTo(itemOptionGroupId2);
    }

}
