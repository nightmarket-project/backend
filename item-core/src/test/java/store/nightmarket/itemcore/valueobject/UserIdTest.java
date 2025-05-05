package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserIdTest {
    @Test
    @DisplayName("같은 UUID로 생성한 RegistrantId는 equals 비교에서 같다")
    void shouldBeEqual_WhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();
        UserId userId1 = new UserId(id);
        UserId userId2 = new UserId(id);

        assertThat(userId1).isEqualTo(userId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 RegistrantId는 equals 비교에서 다르다")
    void shouldNotBeEqual_WhenUUIDsAreDifferent() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        UserId userId1 = new UserId(id1);
        UserId userId2 = new UserId(id2);

        assertThat(userId1).isNotEqualTo(userId2);
    }

}
