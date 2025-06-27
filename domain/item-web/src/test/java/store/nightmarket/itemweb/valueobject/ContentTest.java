package store.nightmarket.itemweb.valueobject;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

class ContentTest {

    @Test
    @DisplayName("PostContent text가 빈 문자열을 가지면 예외 발생한다.")
    void shouldThrowExceptionWhenTextIsBlank() {
        //given
        String blankText = " ";

        //when & then
        assertThatThrownBy(
            () -> TestObjectFactory.createPostContent(blankText)
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("PostContent text 최대 문자 길이보다 크면 예외 발생")
    void shouldThrowExceptionWhenTextIsBiggerThanMaxLength() {
        //given
        String text = "a".repeat(256);

        //when & then
        assertThatThrownBy(
            () -> TestObjectFactory.createPostContent(text)
        ).isInstanceOf(ItemWebException.class);
    }

}
