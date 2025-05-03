package store.nightmarket.itemweb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.RegistrantId;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.valueobject.ReviewId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ReplyTest {

    @Test
    @DisplayName("Reply 생성 테스트")
    void createReplyTest() {
        Reply reply = newReply("hello");

        assertThat(reply).isNotNull();
        assertThat(reply).isInstanceOf(Reply.class);
    }

    @Test
    @DisplayName("Reply content가 blank면 예외 발생")
    void blankContentReplyTest() {

        assertThatThrownBy(() ->  newReply(" ")).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("Reply content의 최대 길이보다 크면 예외 발생")
    void maxContentReplyTest() {
        String content = "a".repeat(256);

        assertThatThrownBy(() -> newReply(content)).isInstanceOf(ItemWebException.class);
    }

    private Reply newReply(String content) {
        return Reply.newInstance(
                new ReviewId(UUID.randomUUID()),
                content,
                new RegistrantId(UUID.randomUUID())
        );
    }

}