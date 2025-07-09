package store.nightmarket.domain.item.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.domain.item.exception.QuantityException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuantityTest {

    @Test
    @DisplayName("같은 값으로 생성한 Quantity는 equals 비교에서 같다")
    void shouldBeEqualWhenValueAreEqual() {
        //given
        BigDecimal value = new BigDecimal(100);

        //when
        Quantity quantity1 = new Quantity(value);
        Quantity quantity2 = new Quantity(value);

        //then
        assertThat(quantity1).isEqualTo(quantity2);
    }

    @Test
    @DisplayName("다른 값으로 생성한 Quantity는 equals 비교에서 다르다")
    void shouldNotBeEqualWhenValuesAreDifferent() {
        //given
        BigDecimal value1 = new BigDecimal(101);
        BigDecimal value2 = new BigDecimal(102);

        //when
        Quantity quantity1 = new Quantity(value1);
        Quantity quantity2 = new Quantity(value2);

        //then
        assertThat(quantity1).isNotEqualTo(quantity2);
    }

    @Test
    @DisplayName("Quantity의 value가 null 값이면 예외가 발생한다")
    void shouldThrowExceptionWhenQuantityValueIsNull() {
        //given
        BigDecimal value = null;

        //when & then
        assertThatThrownBy(() -> new Quantity(value)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity은 0보다 작은 값이면 예외가 발생한다.")
    void shouldThrowExceptionWhenQuantityValueIsLessThanZero() {
        //given
        BigDecimal value = BigDecimal.valueOf(-1);

        //when & then
        assertThatThrownBy(() -> new Quantity(value)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity는 최대 수량 값보다 크면 예외가 발생한다.")
    void shouldThrowExceptionWhenQuantityValueIsBiggerThanMaxValue() {
        //given
        BigDecimal max = BigDecimal.valueOf(Integer.MAX_VALUE);

        //when & then
        assertThatThrownBy(() -> new Quantity(max)).isInstanceOf(QuantityException.class);
    }

    @Test
    @DisplayName("Quantity끼리 add하면 두 값의 합을 가진 새로운 Quantity를 반환한다.")
    void shouldReturnNewQuantityWithSumWhenTwoQuantitiesAreAdded() {
        //given
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(1000));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(500));

        //when
        Quantity result = quantity1.add(quantity2);

        //then
        assertThat(result).isEqualTo(new Quantity(BigDecimal.valueOf(1500)));
    }

    @Test
    @DisplayName("Quantity끼리 subtract하면 두 값의 뺀 결과 값을 가진 새로운 Quantity를 반환한다.")
    void shouldReturnNewQuantityWithSubtractWhenTwoQuantitiesAreSubtracted() {
        //given
        Quantity quantity1 = new Quantity(BigDecimal.valueOf(1000));
        Quantity quantity2 = new Quantity(BigDecimal.valueOf(500));

        //when
        Quantity result = quantity1.subtract(quantity2);

        //then
        assertThat(result).isEqualTo(new Quantity(BigDecimal.valueOf(500)));
    }

    @Test
    @DisplayName("Quantity의 value가 다른 Quantity의 value보다 크거나 같으면 참이다.")
    void ShoudReturnTrueWhenThisQuantityIsGreaterThanOrEqualToOtherQuantity() {
        //given
        Quantity quantity1 = new Quantity(BigDecimal.TEN);
        Quantity quantity2 = new Quantity(BigDecimal.ZERO);
        Quantity quantity3 = new Quantity(BigDecimal.TEN);

        //when
        boolean result = quantity1.isGreaterThanOrEqualTo(quantity2);
        boolean result2 = quantity1.isGreaterThanOrEqualTo(quantity3);

        //then
        assertThat(result).isTrue();
        assertThat(result2).isTrue();
    }

}
