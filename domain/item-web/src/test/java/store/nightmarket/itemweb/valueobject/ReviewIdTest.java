package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ReviewIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();

        ReviewId ReviewId1 = new ReviewId(id);
        ReviewId ReviewId2 = new ReviewId(id);

        assertThat(ReviewId1).isEqualTo(ReviewId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        ReviewId ReviewId1 = new ReviewId(UUID.randomUUID());
        ReviewId ReviewId2 = new ReviewId(UUID.randomUUID());

        assertThat(ReviewId1).isNotEqualTo(ReviewId2);
    }

}
