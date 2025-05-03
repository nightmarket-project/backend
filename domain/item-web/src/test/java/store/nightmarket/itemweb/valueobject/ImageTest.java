package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.Name;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTest {

    @Test
    @DisplayName("Image 객체 생성 테스트")
    void createImageTest() {
        String url = "https://picsum.photos/200/300";
        Image image1 = newInstanceImage(url, 1);

        assertThat(image1).isNotNull();
        assertThat(image1).isInstanceOf(Image.class);
    }

    @Test
    @DisplayName("같은 url과 order로 생성된 Image는 같다")
    void imageEqualTest() {
        String url = "https://picsum.photos/200/300";
        Image image1 = newInstanceImage(url, 1);
        Image image2 = newInstanceImage(url, 1);

        assertThat(image1).isEqualTo(image2);
    }

    @Test
    @DisplayName("다른 url로 생성된 Image는 다르다")
    void imageNotEqualTest() {
        String url1 = "https://picsum.photos/200/300";
        String url2 = "https://picsum.photos/300/400";
        Image image1 = newInstanceImage(url1, 1);
        Image image2 = newInstanceImage(url2, 2);

        assertThat(image1).isNotEqualTo(image2);
    }

    private Image newInstanceImage(String url, int order) {
        return new Image(
                url,
                new Name("example-image.jpg"),
                order
        );
    }
}