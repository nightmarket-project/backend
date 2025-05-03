package store.nightmarket.itemweb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.valueobject.ItemId;
import store.nightmarket.itemweb.valueobject.PostContent;
import store.nightmarket.itemweb.valueobject.ProductPostId;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProductPostTest {

    @Test
    @DisplayName("ProductPost 생성 테스트")
    void craeteProductPostTest() {
        ProductPost productPost = ProductPost.newInstance(
                new ProductPostId(UUID.randomUUID()),
                new ItemId(UUID.randomUUID()),
                mock(PostContent.class),
                List.of(mock(Review.class))
        );

        assertThat(productPost).isNotNull();
        assertThat(productPost).isInstanceOf(ProductPost.class);
    }
}