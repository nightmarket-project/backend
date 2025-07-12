package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.service.dto.EditReplyItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.EditReplyItemWebDomainServiceDto.Input;
import store.nightmarket.itemweb.valueobject.CommentText;

class EditReplyItemWebDomainServiceTest {

    private EditReplyItemWebDomainService service;

    @BeforeEach
    void setUp() {
        service = new EditReplyItemWebDomainService();
    }

    @Test
    @DisplayName("대댓글 작성자와 요청한 사용자 ID가 같으면 대댓글이 수정된다")
    void shouldEditReplyWhenUserIdIsEqualToReplyAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        CommentText editingText = new CommentText("bad!");
        Reply reply = TestObjectFactory.createReply(
            UUID.randomUUID(),
            "good!",
            authorId,
            UUID.randomUUID()
        );

        Input input = Input.builder()
            .reply(reply)
            .userId(new UserId(authorId))
            .commentText(editingText)
            .build();

        // when
        Event event = service.execute(input);

        // then
        assertThat(event.getReply().getCommentText())
            .isEqualTo(editingText);
    }

    @Test
    @DisplayName("대댓글 작성자가 아닌 사용자가 수정하려고 하면 예외가 발생한다")
    void shouldThrowExceptionWhenUserIdIsDifferentFromReplyAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        UserId otherAuthorId = new UserId(UUID.randomUUID());
        CommentText editingText = new CommentText("bad!");
        Reply reply = TestObjectFactory.createReply(
            UUID.randomUUID(),
            "good!",
            authorId,
            UUID.randomUUID()
        );

        Input input = Input.builder()
            .reply(reply)
            .userId(otherAuthorId)
            .commentText(editingText)
            .build();

        // when
        // then
        assertThatThrownBy(
            () -> service.execute(input)
        ).isInstanceOf(ItemWebException.class);
    }

}
