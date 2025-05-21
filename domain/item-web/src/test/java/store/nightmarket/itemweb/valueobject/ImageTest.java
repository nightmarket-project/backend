package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ImageTest {

    @Test
    @DisplayName("같은 url과 order로 생성된 Image는 equals 비교에서 같다")
    void shouldBeEqualWhenUrlAndOrderAreEqual() {
        //given
        String url = "url";
        int order = 1;

        //when
        Image image1 = TestObjectFactory.createImage(url, order);
        Image image2 = TestObjectFactory.createImage(url, order);

        //then
        assertThat(image1).isEqualTo(image2);
    }

    @Test
    @DisplayName("Image 어떤 url, order가 다르면 equals 비교에서 다르다")
    void shouldNotBeEqualWhenAnyUrlAndAnyOrderAreNotEqual() {
        //given & when
        Image image1 = TestObjectFactory.defaultImage();
        Image image2 = TestObjectFactory.defaultImage();

        //then
        assertThat(image1).isNotEqualTo(image2);
    }

}
