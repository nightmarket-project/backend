package store.nightmarket.itemweb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReplyTest {

    @Test
    @DisplayName("Reply 생성 테스트")
    void shouldCreateReplySuccessfully() {
        Reply reply = TestObjectFactory.defaultReply();

        assertThat(reply).isNotNull();
        assertThat(reply).isInstanceOf(Reply.class);
    }

    @Test
    @DisplayName("Reply content가 blank면 예외 발생")
    void shouldThrowExceptionWhenReplyContentIsBlank() {

        assertThatThrownBy(
                () -> TestObjectFactory.createReply(" ")
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("Reply content의 최대 길이보다 크면 예외 발생")
    void shouldThrowExceptionWhenReplyContentIsLongerThanMaxLength() {
        String content = "a".repeat(256);

        assertThatThrownBy(
                () -> TestObjectFactory.createReply(content)
        ).isInstanceOf(ItemWebException.class);
    }

}
