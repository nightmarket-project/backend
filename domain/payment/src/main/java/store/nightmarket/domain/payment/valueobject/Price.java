package store.nightmarket.domain.payment.valueobject;

import java.math.BigDecimal;

public class Price {

	private final BigDecimal amount;

	public Price(BigDecimal amount) {
		this.amount = amount;
	}

}
