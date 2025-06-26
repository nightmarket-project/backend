package store.nightmarket.domain.item.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.NameException;

import static org.assertj.core.api.Assertions.*;

class NameTest {

    @Test
    @DisplayName("같은 value로 생성한 Name은 equals 비교에서 같다")
    void shouldBeEqualWhenValuesAreEqual() {
        //given
        String value = "ACICS";

        //when
        Name name1 = new Name(value);
        Name name2 = new Name(value);

        //then
        assertThat(name1).isEqualTo(name2);
    }

    @Test
    @DisplayName("다른 value로 생성한 Name은 equals 비교에서 다르다")
    void shouldNotBeEqualWhenValuesAreDifferent() {
        //given
        String value1 = "ACICS";
        String value2 = "NIKE";

        //when
        Name name1 = new Name(value1);
        Name name2 = new Name(value2);

        //then
        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("Name 생성 시 이름이 null이면 예외가 발생한다")
    void shouldThrowExceptionWhenNameValueIsNull() {
        //given
        String value = null;

        //when & then
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(NameException.class);
    }

    @Test
    @DisplayName("Name 생성 시 이름이 비어 있으면 예외가 발생한다.")
    void shouldThrowExceptionWhenNameValueIsBlank() {
        //given
        String value = "";

        //when & then
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(NameException.class);
    }

    @Test
    @DisplayName("Name은 길이가 64보다 크면 예외가 발생한다.")
    void shouldThrowExceptionWhenNameValueIsLongerThan64() {
        //given
        String value = "a".repeat(65);

        //when & then
        assertThatThrownBy(() -> new Name(value)).isInstanceOf(NameException.class);
    }

}
