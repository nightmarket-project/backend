package store.nightmarket.itemweb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewTest {

    @Test
    @DisplayName("Review 생성 테스트")
    void shouldCreateReviewSuccessfully() {
        Review review = TestObjectFactory.defaultReview();

        assertThat(review).isNotNull();
        assertThat(review).isInstanceOf(Review.class);
    }

}
