package store.nightmarket.itemweb.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;
import store.nightmarket.itemweb.model.Reply;
import store.nightmarket.itemweb.service.dto.DeleteReplyItemWebDomainServiceDto.Event;
import store.nightmarket.itemweb.service.dto.DeleteReplyItemWebDomainServiceDto.Input;

class DeleteReplyItemWebDomainServiceTest {

    private DeleteReplyItemWebDomainService service;
    private SoftAssertions softly;

    @BeforeEach
    void setUp() {
        service = new DeleteReplyItemWebDomainService();
        softly = new SoftAssertions();
    }

    @Test
    @DisplayName("서비스 실행 시 대댓글을 삭제 처리하고 내용은 '삭제된 댓글 입니다.'로 변경한다")
    void shouldSoftDeleteReplyAndMaskContentWhenExecuted() {
        // given
        UUID authorId = UUID.randomUUID();
        Reply reply = TestObjectFactory.createReply(
            UUID.randomUUID(),
            "good!",
            authorId,
            UUID.randomUUID()
        );

        Input input = Input.builder()
            .userId(new UserId(authorId))
            .reply(reply)
            .build();

        // when
        Event event = service.execute(input);

        // then
        softly.assertThat(event)
            .isNotNull();
        softly.assertThat(event.getReply().isDeleted())
            .isTrue();
        softly.assertThat(event.getReply().getCommentText().getValue())
            .isEqualTo("삭제된 댓글입니다.");
        softly.assertAll();
    }

    @Test
    @DisplayName("요청한 사용자 ID가 대댓글 작성자와 다르면 예외가 발생한다")
    void shouldThrowExceptionWhenUserIdIsDifferentFromReplyAuthorId() {
        // given
        UUID authorId = UUID.randomUUID();
        UserId otherAuthorId = new UserId(UUID.randomUUID());
        Reply reply = TestObjectFactory.createReply(
            UUID.randomUUID(),
            "good!",
            authorId,
            UUID.randomUUID()
        );

        Input input = Input.builder()
            .userId(otherAuthorId)
            .reply(reply)
            .build();

        // when
        // then
        assertThatThrownBy(() -> service.execute(input))
            .isInstanceOf(ItemWebException.class);
    }

}
