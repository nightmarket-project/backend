package store.nightmarket.itemweb.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

class ReviewTest {

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("현재 유저 아이디와 작성자 아이디가 같을때 리뷰가 삭제된다.")
    void shouldDeleteReviewWhenCurrentUserIdIsEqualToAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            "good!",
            TestObjectFactory.defaultImage(),
            5
        );

        // when
        review.delete(new UserId(authorId));

        // then
        softly.assertThat(review.isDeleted()).isTrue();
        softly.assertThat(review.getContent().getText()).isEqualTo("삭제된 댓글 입니다.");
        softly.assertAll();
    }

    @Test
    @DisplayName("현재 유저 아이디와 작성자 아이디가 다를때 예외가 발생한다.")
    void shouldThrowExceptionWhenCurrentUserIdIsNotEqualToAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();
        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            "good!",
            TestObjectFactory.defaultImage(),
            5
        );

        // when
        // then
        assertThatThrownBy(() -> review.delete(new UserId(otherUserId)))
            .isInstanceOf(ItemWebException.class);
    }

}
