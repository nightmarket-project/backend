package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ReviewIdTest {

    @Test
    @DisplayName("ReviewId 생성 테스트")
    void createReviewId() {
        ReviewId ReviewId = new ReviewId(UUID.randomUUID());

        assertThat(ReviewId).isNotNull();
        assertThat(ReviewId).isInstanceOf(ReviewId.class);
    }

    @Test
    @DisplayName("ReviewId 같은 id이면 같다.")
    void equalsReviewId() {
        UUID id = UUID.randomUUID();

        ReviewId ReviewId1 = new ReviewId(id);
        ReviewId ReviewId2 = new ReviewId(id);

        assertThat(ReviewId1).isEqualTo(ReviewId2);
    }

    @Test
    @DisplayName("ReviewId 다른 id이면 다르다.")
    void notEqualsReviewId() {
        ReviewId ReviewId1 = new ReviewId(UUID.randomUUID());
        ReviewId ReviewId2 = new ReviewId(UUID.randomUUID());

        assertThat(ReviewId1).isNotEqualTo(ReviewId2);

    }
}