package store.nightmarket.domain.payment.valueobject;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class Price {

	private final BigDecimal price;

	public Price(BigDecimal price) {
		this.price = price;
	}

}
