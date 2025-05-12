package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ItemIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        //given
        UUID id = UUID.randomUUID();

        //when
        ItemId itemId1 = new ItemId(id);
        ItemId itemId2 = new ItemId(id);

        //then
        assertThat(itemId1).isEqualTo(itemId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        //given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        //when
        ItemId itemId1 = new ItemId(id1);
        ItemId itemId2 = new ItemId(id2);

        //then
        assertThat(itemId1).isNotEqualTo(itemId2);
    }

}
