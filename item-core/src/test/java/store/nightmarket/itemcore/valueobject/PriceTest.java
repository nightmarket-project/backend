package store.nightmarket.itemcore.valueobject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.nightmarket.itemcore.exception.PriceException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @Test
    @DisplayName("같은 값으로 생성한 Price는 equals 비교에서 같다")
    void PriceEqualTest() {
        BigDecimal amount = new BigDecimal(100);

        Price price1 = new Price(amount);
        Price price2 = new Price(amount);

        assertThat(price1).isEqualTo(price2);
    }

    @Test
    @DisplayName("다른 값으로 생성한 Price는 equals 비교에서 다르다")
    void PriceNotEqualTest() {
        BigDecimal amount1 = new BigDecimal(101);
        BigDecimal amount2 = new BigDecimal(102);

        Price price1 = new Price(amount1);
        Price price2 = new Price(amount2);

        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    @DisplayName("Price은 null 값이 안된다")
    void PriceNullValidationTest() {
        assertThatThrownBy(() -> new Price(null)).isInstanceOf(PriceException.class);
    }

    @Test
    @DisplayName("Price은 0보다 작은 값이 안된다")
    void PriceLessZeroValidationTest() {
        assertThatThrownBy(() -> new Price(BigDecimal.ZERO)).isInstanceOf(PriceException.class);
    }

    @Test
    @DisplayName("Price를 더하면 금액이 합산된다")
    void PriceAddTest() {
        Price price1 = new Price(BigDecimal.valueOf(1000));
        Price price2 = new Price(BigDecimal.valueOf(500));

        Price result = price1.add(price2);

        assertThat(result).isEqualTo(new Price(BigDecimal.valueOf(1500)));
    }

    @Test
    @DisplayName("Price를 빼면 금액이 차감된다")
    void PriceSubtractTest() {
        Price price1 = new Price(BigDecimal.valueOf(1000));
        Price price2 = new Price(BigDecimal.valueOf(500));

        Price result = price1.subtract(price2);

        assertThat(result).isEqualTo(new Price(BigDecimal.valueOf(500)));
    }

}