package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrantIdTest {
    @Test
    @DisplayName("같은 UUID로 생성한 RegistrantId는 equals 비교에서 같다")
    void shouldBeEqual_WhenUUIDsAreEqual() {
        UUID id = UUID.randomUUID();
        RegistrantId registrantId1 = new RegistrantId(id);
        RegistrantId registrantId2 = new RegistrantId(id);

        assertThat(registrantId1).isEqualTo(registrantId2);
    }

    @Test
    @DisplayName("다른 UUID로 생성한 RegistrantId는 equals 비교에서 다르다")
    void shouldNotBeEqual_WhenUUIDsAreDifferent() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        RegistrantId registrantId1 = new RegistrantId(id1);
        RegistrantId registrantId2 = new RegistrantId(id2);

        assertThat(registrantId1).isNotEqualTo(registrantId2);
    }
}