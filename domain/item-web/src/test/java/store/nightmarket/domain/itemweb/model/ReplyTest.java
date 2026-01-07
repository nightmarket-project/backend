package store.nightmarket.domain.itemweb.model;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.exception.ItemWebException;
import store.nightmarket.domain.itemweb.fixture.TestFactory;
import store.nightmarket.domain.itemweb.valueobject.CommentText;

class ReplyTest {

	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("현재 유저 아이디와 작성자 아이디가 같을때 대댓글이 삭제된다.")
	void shouldDeleteReplyWhenCurrentUserIdIsEqualToAuthorId() {
		// given
		UUID authorId = UUID.randomUUID();
		Reply reply = TestFactory.createReply(
			UUID.randomUUID(),
			"good!",
			authorId,
			UUID.randomUUID()
		);

		// when
		reply.delete(new UserId(authorId));

		// then
		softly.assertThat(reply.isDeleted())
			.isTrue();
		softly.assertThat(reply.getCommentText().getValue()).isEqualTo("삭제된 댓글입니다.");
		softly.assertAll();
	}

	@Test
	@DisplayName("대댓글 작성자가 아닌 사용자가 대댓글를 삭제하려고 하면 예외가 발생한다")
	void shouldThrowExceptionWhenUserIdIsDifferentFromAuthorIdOnDeleteReply() {
		// given
		UUID authorId = UUID.randomUUID();
		UserId otherAuthorId = new UserId(UUID.randomUUID());
		Reply reply = TestFactory.createReply(
			UUID.randomUUID(),
			"good!",
			authorId,
			UUID.randomUUID()
		);

		// when
		// then
		assertThatThrownBy(() -> reply.delete(otherAuthorId))
			.isInstanceOf(ItemWebException.class);
	}

	@Test
	@DisplayName("현재 유저 아이디와 작성자 아이디가 같을때 대댓글이 수정된다.")
	void shouldEditReplyWhenCurrentUserIdIsEqualToAuthorId() {
		// given
		UUID authorId = UUID.randomUUID();
		CommentText editingText = new CommentText("bad!");
		Reply reply = TestFactory.createReply(
			UUID.randomUUID(),
			"good!",
			authorId,
			UUID.randomUUID()
		);

		// when
		reply.edit(new UserId(authorId), editingText);

		// then
		assertThat(reply.getCommentText()).isEqualTo(editingText);
	}

	@Test
	@DisplayName("리뷰 작성자가 아닌 사용자가 리뷰를 수정하려고 하면 예외가 발생한다")
	void shouldThrowExceptionWhenUserIdIsDifferentFromAuthorIdOnEditReply() {
		// given
		UUID authorId = UUID.randomUUID();
		UserId otherAuthorId = new UserId(UUID.randomUUID());
		CommentText editingText = new CommentText("bad!");
		Reply reply = TestFactory.createReply(
			UUID.randomUUID(),
			"good!",
			authorId,
			UUID.randomUUID()
		);

		// when
		// then
		assertThatThrownBy(
			() -> reply.edit(
				otherAuthorId,
				editingText
			)
		).isInstanceOf(ItemWebException.class);
	}

}
