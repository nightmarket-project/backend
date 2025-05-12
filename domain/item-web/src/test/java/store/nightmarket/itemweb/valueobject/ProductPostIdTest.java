package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductPostIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();

        ProductPostId productPostId1 = new ProductPostId(id);
        ProductPostId productPostId2 = new ProductPostId(id);

        assertThat(productPostId1).isEqualTo(productPostId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        ProductPostId productPostId1 = new ProductPostId(UUID.randomUUID());
        ProductPostId productPostId2 = new ProductPostId(UUID.randomUUID());

        assertThat(productPostId1).isNotEqualTo(productPostId2);
    }

}
