package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.exception.ItemWebException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PostContentTest {

    @Test
    @DisplayName("PostContent 생성 테스트 ")
    void shouldCreatePostContentSuccessfully() {
        PostContent postContent = newInstance("hello", 2);

        assertThat(postContent).isNotNull();
        assertThat(postContent).isInstanceOf(PostContent.class);
    }

    @Test
    @DisplayName("PostContent text가 빈 문자열을 가지면 예외 발생한다.")
    void shouldThrowExceptionWhenTextIsBlank() {
        assertThatThrownBy(() -> newInstance("", 2)).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("PostContent text 최대 문자 길이보다 크면 예외 발생")
    void shouldThrowExceptionWhenTextIsBiggerThanMaxLength() {
        String text = "a".repeat(256);
        assertThatThrownBy(() -> newInstance(text, 2)).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("PostContent Image 최대 개수보다 크면 예외 발생")
    void shouldThrowExceptionWhenImageQuantityIsBiggerThanMaxQuantity() {
        assertThatThrownBy(() -> newInstance("hello", 11)).isInstanceOf(ItemWebException.class);
    }

    @Test
    @DisplayName("같은 text와 images로 생성한 PostContent은 equals 비교에서 같다")
    void shouldBeEqualWhenTextAndImagesAreEqual() {
        String text = "equal";
        Image image = mock(Image.class);
        PostContent postContent1 = new PostContent(text, List.of(image));
        PostContent postContent2 = new PostContent(text, List.of(image));

        assertThat(postContent1).isEqualTo(postContent2);
    }

    @Test
    @DisplayName("PostContent 어떤 text, images가 다르면 equals 비교에서 다르다")
    void shouldNotBeEqualWhenAnyTextAndAnyImagesAreNotEqual() {
        String text = "notEqual";
        Image image1 = mock(Image.class);
        Image image2 = mock(Image.class);
        PostContent postContent1 = new PostContent(text, List.of(image1));
        PostContent postContent2 = new PostContent(text, List.of(image2));

        assertThat(postContent1).isNotEqualTo(postContent2);
    }

    private PostContent newInstance(String text, int imageSize) {
        List<Image> images = IntStream.range(0, imageSize)
                .mapToObj(i -> mock(Image.class))
                .collect(Collectors.toList());

        return new PostContent(text, images);
    }

}
