package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.QuantityException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuantityTest {

    @Test
    @DisplayName("같은 값으로 생성한 Quantity는 equals 비교에서 같다")
    void QuantityEqualTest() {
        BigDecimal value = new BigDecimal(100);

        Quantity quantity1 = new Quantity(value);
        Quantity quantity2 = new Quantity(value);

        assertThat(quantity1).isEqualTo(quantity2);
    }

    @Test
    @DisplayName("다른 값으로 생성한 Quantity는 equals 비교에서 다르다")
    void QuantityNotEqualTest() {
        BigDecimal value1 = new BigDecimal(101);
        BigDecimal value2 = new BigDecimal(102);

        Quantity quantity1 = new Quantity(value1);
        Quantity quantity2 = new Quantity(value2);

        assertThat(quantity1).isNotEqualTo(quantity2);
    }

    @Test
    @DisplayName("Quantity은 null 값이 안된다")
    void QuantityNullValidationTest() {
        assertThatThrownBy(() -> new Quantity(null)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity은 0보다 작은 값이 안된다")
    void QuantityLessZeroValidationTest() {
        assertThatThrownBy(() -> new Quantity(BigDecimal.valueOf(-1))).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity는 MAX_QUANTITY보다 작은 값이다.")
    void QuantityMAXQUANTITYTest() {
        BigDecimal max = BigDecimal.valueOf(Integer.MAX_VALUE);
        assertThatThrownBy(() -> new Quantity(max)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity를 더하면 금액이 합산된다")
    void QuantityAddTest() {
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(1000));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(500));

        Quantity result = quantity1.add(quantity2);

        assertThat(result).isEqualTo(new Quantity(BigDecimal.valueOf(1500)));
    }

    @Test
    @DisplayName("Quantity를 빼면 금액이 차감된다")
    void QuantitySubtractTest() {
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(1000));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(500));

        Quantity result = quantity1.subtract(quantity2);

        assertThat(result).isEqualTo(new Quantity(BigDecimal.valueOf(500)));
    }

    @Test
    @DisplayName("Quantity의 value가 0이면 참이다.")
    void isZeroTest() {
        Quantity quantity = new Quantity(BigDecimal.ZERO);
        boolean result = quantity.isZero();
        assertThat(result).isTrue();
    }
}