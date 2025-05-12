package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.exception.ItemWebException;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostContentTest {

    @Test
    @DisplayName("PostContent text가 빈 문자열을 가지면 예외 발생한다.")
    void shouldThrowExceptionWhenTextIsBlank() {
        assertThatThrownBy(
                () -> TestObjectFactory.createPostContent(
                        " ",
                        List.of(TestObjectFactory.defaultImage())
                )
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("PostContent text 최대 문자 길이보다 크면 예외 발생")
    void shouldThrowExceptionWhenTextIsBiggerThanMaxLength() {
        String text = "a".repeat(256);
        assertThatThrownBy(
                () -> TestObjectFactory.createPostContent(
                        text,
                        List.of(TestObjectFactory.defaultImage())
                )
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("PostContent Image 최대 개수보다 크면 예외 발생")
    void shouldThrowExceptionWhenImageQuantityIsBiggerThanMaxQuantity() {
        List<Image> images = IntStream.range(0, 11)
                .mapToObj(i -> TestObjectFactory.defaultImage())
                .collect(Collectors.toList());
        assertThatThrownBy(
                () -> TestObjectFactory.createPostContent("hello", images)
        ).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("같은 text와 images로 생성한 PostContent은 equals 비교에서 같다")
    void shouldBeEqualWhenTextAndImagesAreEqual() {
        String text = "equal";
        Image image = TestObjectFactory.defaultImage();
        PostContent postContent1 = new PostContent(text, List.of(image));
        PostContent postContent2 = new PostContent(text, List.of(image));

        assertThat(postContent1).isEqualTo(postContent2);
    }

    @Test
    @DisplayName("PostContent 어떤 text, images가 다르면 equals 비교에서 다르다")
    void shouldNotBeEqualWhenAnyTextAndAnyImagesAreNotEqual() {
        String text1 = "notEqual1";
        String text2 = "notEqual2";
        Image image = TestObjectFactory.defaultImage();
        PostContent postContent1 = new PostContent(text1, List.of(image));
        PostContent postContent2 = new PostContent(text2, List.of(image));

        assertThat(postContent1).isNotEqualTo(postContent2);
    }

}
