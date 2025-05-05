package store.nightmarket.itemweb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.UserId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ReviewId;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ReviewTest {

    @Test
    @DisplayName("Review 생성 테스트")
    void shouldCreateReview_Successfully() {
        Review review = Review.newInstance(
                new ReviewId(UUID.randomUUID()),
                new UserId(UUID.randomUUID()),
                mock(PostContent.class),
                4,
                List.of(mock(Reply.class))
        );

        assertThat(review).isNotNull();
        assertThat(review).isInstanceOf(Review.class);
    }
}
