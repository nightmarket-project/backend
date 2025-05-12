package store.nightmarket.itemweb.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductPostIdTest {

    @Test
    @DisplayName("같은 UUID로 생성한 ItemId는 equals 비교에서 같다")
    void shouldBeEqualWhenUUIDsAreEqual() {
        //given
        UUID id = UUID.randomUUID();

        //when
        ProductPostId productPostId1 = new ProductPostId(id);
        ProductPostId productPostId2 = new ProductPostId(id);

        //then
        assertThat(productPostId1).isEqualTo(productPostId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 ItemId는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenUUIDsAreDifferent() {
        //given & when
        ProductPostId productPostId1 = new ProductPostId(UUID.randomUUID());
        ProductPostId productPostId2 = new ProductPostId(UUID.randomUUID());

        //then
        assertThat(productPostId1).isNotEqualTo(productPostId2);
    }

}
