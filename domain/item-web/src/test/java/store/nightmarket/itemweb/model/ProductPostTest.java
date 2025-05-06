package store.nightmarket.itemweb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemweb.fixture.TestObjectFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ProductPostTest {

    @Test
    @DisplayName("ProductPost 생성 테스트")
    void shouldCreateProductPostSuccessfully() {
        ProductPost productPost = TestObjectFactory.defaultProductPost();

        assertThat(productPost).isNotNull();
        assertThat(productPost).isInstanceOf(ProductPost.class);
    }

}
