package store.nightmarket.itemweb.model;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReplyTest {

    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("Reply content가 blank면 예외 발생")
    void shouldThrowExceptionWhenReplyContentIsBlank() {
        //given
        String replyContent = " ";

        //when & then
        assertThatThrownBy(
                () -> TestObjectFactory.createReply(replyContent)
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("Reply content의 최대 길이보다 크면 예외 발생")
    void shouldThrowExceptionWhenReplyContentIsLongerThanMaxLength() {
        //given
        String content = "a".repeat(256);

        //when & then
        assertThatThrownBy(
                () -> TestObjectFactory.createReply(content)
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("현재 유저 아이디와 작성자 아이디가 같을때 대댓글이 삭제된다.")
    void shouldDeleteReplyWhenCurrentUserIdIsEqualToAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        Reply reply = TestObjectFactory.createReply(
            UUID.randomUUID(),
            "good!",
            authorId,
            UUID.randomUUID()
        );

        // when
        reply.delete(new UserId(authorId));

        // then
        softly.assertThat(reply.isDeleted()).isTrue();
        softly.assertThat(reply.getContent().getText()).isEqualTo("삭제된 댓글 입니다.");
        softly.assertAll();
    }

}
