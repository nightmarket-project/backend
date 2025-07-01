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
import store.nightmarket.itemweb.valueobject.Image;
import store.nightmarket.itemweb.valueobject.ReviewContent;

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
            TestObjectFactory.createReviewContent(
                "good!",
                5,
                TestObjectFactory.defaultImage()
            )
        );

        // when
        review.delete(new UserId(authorId));

        // then
        softly.assertThat(review.isDeleted()).isTrue();
        softly.assertThat(review.getReviewContent().getDescription()).isEqualTo("삭제된 댓글입니다.");
        softly.assertAll();
    }

    @Test
    @DisplayName("리뷰 작성자가 아닌 사용자가 리뷰를 삭제하려고 하면 예외가 발생한다")
    void shouldThrowExceptionWhenUserIdIsDifferentFromAuthorIdOnDeleteReview() {
        // given
        UUID authorId = UUID.randomUUID();
        UUID otherUserId = UUID.randomUUID();

        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            TestObjectFactory.createReviewContent(
                "good!",
                5,
                TestObjectFactory.defaultImage()
            )
        );

        // when
        // then
        assertThatThrownBy(() -> review.delete(new UserId(otherUserId)))
            .isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("현재 유저 아이디와 작성자 아이디가 같을때 리뷰가 수정된다.")
    void shouldEditReviewWhenCurrentUserIdIsEqualToAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        Image image = TestObjectFactory.createImage("aaa", "테스트 사진", 1);

        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            TestObjectFactory.createReviewContent(
                "good!",
                5,
                image
            )
        );

        // when
        ReviewContent editReviewContent = TestObjectFactory.createReviewContent(
            "bad!",
            1,
            TestObjectFactory.defaultImage()
        );

        review.edit(
            new UserId(authorId),
            editReviewContent
        );

        // then
        softly.assertThat(review.getReviewContent())
            .isEqualTo(editReviewContent);
        softly.assertAll();
    }

    @Test
    @DisplayName("리뷰 작성자가 아닌 사용자가 리뷰를 수정하려고 하면 예외가 발생한다")
    void shouldThrowExceptionWhenUserIdIsDifferentFromAuthorIdOnEditReview() {
        // given
        UUID authorId = UUID.randomUUID();
        Image image = TestObjectFactory.createImage("aaa", "테스트 사진", 1);

        Review review = TestObjectFactory.createReview(
            UUID.randomUUID(),
            authorId,
            TestObjectFactory.createReviewContent(
                "good!",
                5,
                image
            )
        );

        // when
        // then
        UserId otherUserId = new UserId(UUID.randomUUID());
        ReviewContent editReviewContent = TestObjectFactory.createReviewContent(
            "bad!",
            1,
            TestObjectFactory.defaultImage()
        );

        assertThatThrownBy(
            () -> review.edit(
                otherUserId,
                editReviewContent
            )
        ).isInstanceOf(ItemWebException.class);
    }

}
