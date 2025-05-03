package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.NameException;

import static org.assertj.core.api.Assertions.*;

class NameTest {

    @Test
    @DisplayName("같은 value로 생성한 Name은 equals 비교에서 같다")
    void shouldBeEqual_WhenUUIDsAreEqual() {
        String value = "ACICS";

        Name name1 = new Name(value);
        Name name2 = new Name(value);

        assertThat(name1).isEqualTo(name2);
    }

    @Test
    @DisplayName("다른 value로 생성한 Name은 equals 비교에서 다르다")
    void shouldNotBeEqual_WhenUUIDsAreDifferent() {
        String value1 = "ACICS";
        String value2 = "NIKE";

        Name name1 = new Name(value1);
        Name name2 = new Name(value2);

        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("Name 생성 시 이름이 null이면 예외가 발생한다")
    void NameNullValidationTest() {
        String value = null;

        assertThatThrownBy(() -> new Name(value)).isInstanceOf(NameException.class);
    }

    @Test
    @DisplayName("Name 생성 시 이름이 비어 있으면 예외가 발생한다.")
    void NameEmptyValidationTest() {
        String value = "";

        assertThatThrownBy(() -> new Name(value)).isInstanceOf(NameException.class);
    }

    @Test
    @DisplayName("Name은 길이가 64보다 크면 예외가 발생한다.")
    void NameLengthValidationTest() {
        String value = "a".repeat(65);
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(NameException.class);
    }
}