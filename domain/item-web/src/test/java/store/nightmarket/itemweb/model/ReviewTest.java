package store.nightmarket.itemweb.model;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;
import store.nightmarket.itemweb.valueobject.CommentText;
import store.nightmarket.itemweb.valueobject.Rating;

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
			UUID.randomUUID(),
			authorId,
			"good!",
			5
		);

		// when
		review.delete(new UserId(authorId));

		// then
		softly.assertThat(review.isDeleted()).isTrue();
		softly.assertThat(review.getCommentText().getValue()).isEqualTo("삭제된 댓글입니다.");
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
			UUID.randomUUID(),
			authorId,
			"good!",
			5
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

		Review review = TestObjectFactory.createReview(
			UUID.randomUUID(),
			UUID.randomUUID(),
			authorId,
			"good!",
			5
		);

		// when
		review.edit(
			new UserId(authorId),
			new CommentText("bad!"),
			new Rating(1)
		);

		// then
		softly.assertThat(review.getCommentText().getValue())
			.isEqualTo("bad!");
		softly.assertThat(review.getRating())
			.isEqualTo(new Rating(1));
		softly.assertAll();
	}

	@Test
	@DisplayName("리뷰 작성자가 아닌 사용자가 리뷰를 수정하려고 하면 예외가 발생한다")
	void shouldThrowExceptionWhenUserIdIsDifferentFromAuthorIdOnEditReview() {
		// given
		UUID authorId = UUID.randomUUID();

		Review review = TestObjectFactory.createReview(
			UUID.randomUUID(),
			UUID.randomUUID(),
			authorId,
			"good!",
			5
		);

		// when
		// then
		UserId otherUserId = new UserId(UUID.randomUUID());

		assertThatThrownBy(
			() -> review.edit(
				otherUserId,
				new CommentText("bad!"),
				new Rating(1)
			)
		).isInstanceOf(ItemWebException.class);
	}

}
