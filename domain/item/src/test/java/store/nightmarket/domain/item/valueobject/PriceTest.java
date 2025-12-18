package store.nightmarket.domain.item.valueobject;

import static org.assertj.core.api.Assertions.*;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.domain.item.exception.PriceException;

class PriceTest {

	@Test
	@DisplayName("같은 값으로 생성한 Price는 equals 비교에서 같다")
	void shouldBeEqual_WhenAmountsAreEqual() {
		//given
		BigInteger amount = BigInteger.valueOf(100);

		//when
		Price price1 = new Price(amount);
		Price price2 = new Price(amount);

		//then
		assertThat(price1).isEqualTo(price2);
	}

	@Test
	@DisplayName("다른 값으로 생성한 Price는 equals 비교에서 다르다")
	void shouldNotBeEqual_WhenAmountsAreDifferent() {
		//given
		BigInteger amount1 = BigInteger.valueOf(101);
		BigInteger amount2 = BigInteger.valueOf(102);

		//when
		Price price1 = new Price(amount1);
		Price price2 = new Price(amount2);

		//then
		assertThat(price1).isNotEqualTo(price2);
	}

	@Test
	@DisplayName("Price의 amount는 null 값을 가지면 예외가 발생한다.")
	void shouldThrowException_WhenAmountIsNull() {
		//given
		BigInteger amount = null;

		//when& then
		assertThatThrownBy(() -> new Price(amount)).isInstanceOf(PriceException.class);
	}

	@Test
	@DisplayName("Price의 amount은 0보다 작은 값을 가지면 예외가 발생한다.")
	void shouldThrowException_WhenAmountIsLessThanZero() {
		//given
		BigInteger amount = BigInteger.valueOf(-1);

		//when& then
		assertThatThrownBy(() -> new Price(amount)).isInstanceOf(PriceException.class);
	}

	@Test
	@DisplayName("Price끼리 add하면 두 값의 합을 가진 새로운 Price를 반환한다.")
	void shouldReturnNewPriceWithSumWhenTwoPricesAreAdded() {
		//given
		Price price1 = new Price(BigInteger.valueOf(1000));
		Price price2 = new Price(BigInteger.valueOf(500));

		//when
		Price result = price1.add(price2);

		//then
		assertThat(result).isEqualTo(new Price(BigInteger.valueOf(1500)));
	}

	@Test
	@DisplayName("Price끼리 subtract하면 두 값의 뺀 결과 값을 가진 새로운 Price를 반환한다.")
	void shouldReturnNewPriceWithSubtractWhenTwoPricesAreSubtracted() {
		//given
		Price price1 = new Price(BigInteger.valueOf(1000));
		Price price2 = new Price(BigInteger.valueOf(500));

		//when
		Price result = price1.subtract(price2);

		//then
		assertThat(result).isEqualTo(new Price(BigInteger.valueOf(500)));
	}

}
