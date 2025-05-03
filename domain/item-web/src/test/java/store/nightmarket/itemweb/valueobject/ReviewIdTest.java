package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ReviewIdTest {

    @Test
    @DisplayName("ReviewId 생성 테스트")
    void shouldCreateReviewId_Successfully() {
        ReviewId ReviewId = new ReviewId(UUID.randomUUID());

        assertThat(ReviewId).isNotNull();
        assertThat(ReviewId).isInstanceOf(ReviewId.class);
    }

    @Test
    @DisplayName("같은 UUID로 생성한 ItemId는 equals 비교에서 같다")
    void shouldBeEqual_WhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();

        ReviewId ReviewId1 = new ReviewId(id);
        ReviewId ReviewId2 = new ReviewId(id);

        assertThat(ReviewId1).isEqualTo(ReviewId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemId는 equals 비교에서 다르다")
    void shouldNotBeEqual_WhenUUIDsAreDifferent() {
        ReviewId ReviewId1 = new ReviewId(UUID.randomUUID());
        ReviewId ReviewId2 = new ReviewId(UUID.randomUUID());

        assertThat(ReviewId1).isNotEqualTo(ReviewId2);

    }
}