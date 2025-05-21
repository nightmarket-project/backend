package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OptionIdTest {
    @Test
    @DisplayName("같은 UUID로 생성한 OptionId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        //given
        UUID id = UUID.randomUUID();

        //when
        OptionId optionId1 = new OptionId(id);
        OptionId optionId2 = new OptionId(id);

        //then
        assertThat(optionId1).isEqualTo(optionId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 OptionId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        //given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        //when
        OptionId optionId1 = new OptionId(id1);
        OptionId optionId2 = new OptionId(id2);

        //then
        assertThat(optionId1).isNotEqualTo(optionId2);
    }

}
