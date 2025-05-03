package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductPostIdTest {

    @Test
    @DisplayName("ProductPostId 생성 테스트")
    void createProductPostId() {
        ProductPostId productPostId = new ProductPostId(UUID.randomUUID());

        assertThat(productPostId).isNotNull();
        assertThat(productPostId).isInstanceOf(ProductPostId.class);
    }

    @Test
    @DisplayName("ProductPostId 같은 id이면 같다.")
    void equalsProductPostId() {
        UUID id = UUID.randomUUID();

        ProductPostId productPostId1 = new ProductPostId(id);
        ProductPostId productPostId2 = new ProductPostId(id);

        assertThat(productPostId1).isEqualTo(productPostId2);
    }

    @Test
    @DisplayName("ProductPostId 다른 id이면 다르다.")
    void notEqualsProductPostId() {
        ProductPostId productPostId1 = new ProductPostId(UUID.randomUUID());
        ProductPostId productPostId2 = new ProductPostId(UUID.randomUUID());

        assertThat(productPostId1).isNotEqualTo(productPostId2);

    }
}