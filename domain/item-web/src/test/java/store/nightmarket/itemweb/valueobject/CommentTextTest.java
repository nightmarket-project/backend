package store.nightmarket.itemweb.valueobject;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.exception.ItemWebException;

class CommentTextTest {

    @Test
    @DisplayName("CommentText text 최대 문자 길이보다 크면 예외 발생")
    void shouldThrowExceptionWhenTextIsBiggerThanMaxLength() {
        //given
        String text = "a".repeat(256);

        //when & then
        assertThatThrownBy(
            () -> new CommentText(text)
        ).isInstanceOf(ItemWebException.class);
    }

}
