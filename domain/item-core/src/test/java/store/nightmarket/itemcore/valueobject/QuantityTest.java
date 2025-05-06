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
    void shouldBeEqualWhenValueAreEqual() {
        BigDecimal value = new BigDecimal(100);

        Quantity quantity1 = new Quantity(value);
        Quantity quantity2 = new Quantity(value);

        assertThat(quantity1).isEqualTo(quantity2);
    }

    @Test
    @DisplayName("다른 값으로 생성한 Quantity는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenValuesAreDifferent() {
        BigDecimal value1 = new BigDecimal(101);
        BigDecimal value2 = new BigDecimal(102);

        Quantity quantity1 = new Quantity(value1);
        Quantity quantity2 = new Quantity(value2);

        assertThat(quantity1).isNotEqualTo(quantity2);
    }

    @Test
    @DisplayName("Quantity의 value가 null 값이면 예외가 발생한다")
    void shouldThrowExceptionWhenQuantityValueIsNull() {
        assertThatThrownBy(() -> new Quantity(null)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity은 0보다 작은 값이면 예외가 발생한다.")
    void shouldThrowExceptionWhenQuantityValueIsLessThanZero() {
        assertThatThrownBy(() -> new Quantity(BigDecimal.valueOf(-1))).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity는 최대 수량 값보다 크면 예외가 발생한다.")
    void shouldThrowExceptionWhenQuantityValueIsBiggerThanMaxValue() {
        BigDecimal max = BigDecimal.valueOf(Integer.MAX_VALUE);
        assertThatThrownBy(() -> new Quantity(max)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity끼리 add하면 두 값의 합을 가진 새로운 Quantity를 반환한다.")
    void shouldReturnNewQuantityWithSumWhenTwoQuantitiesAreAdded() {
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(1000));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(500));

        Quantity result = quantity1.add(quantity2);

        assertThat(result).isEqualTo(new Quantity(BigDecimal.valueOf(1500)));
    }

    @Test
    @DisplayName("Quantity끼리 subtract하면 두 값의 뺀 결과 값을 가진 새로운 Quantity를 반환한다.")
    void shouldReturnNewQuantityWithSubtractWhenTwoQuantitiesAreSubtracted() {
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(1000));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(500));

        Quantity result = quantity1.subtract(quantity2);

        assertThat(result).isEqualTo(new Quantity(BigDecimal.valueOf(500)));
    }

    @Test
    @DisplayName("Quantity의 value가 다른 Quantity의 value보다 크거나 같으면 참이다.")
    void ShoudReturnTrueWhenThisQuantityIsGreaterThanOrEqualToOtherQuantity() {
        Quantity quantity1 = new Quantity(BigDecimal.TEN);
        Quantity quantity2 = new Quantity(BigDecimal.ZERO);
        Quantity quantity3 = new Quantity(BigDecimal.TEN);
        boolean result = quantity1.isGreaterThanOrEqualTo(quantity2);
        boolean result2 = quantity1.isGreaterThanOrEqualTo(quantity3);

        assertThat(result).isTrue();
        assertThat(result2).isTrue();
    }

}
