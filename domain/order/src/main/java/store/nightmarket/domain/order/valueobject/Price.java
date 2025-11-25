package store.nightmarket.domain.order.valueobject;

import lombok.Getter;
import store.nightmarket.domain.order.exception.OrderException;

@Getter
public class Price {

	private final long price;

	public Price(long price) {
		if (price <= 0) {
			throw new OrderException("Price must be greater than zero.");
		}
		this.price = price;
	}

}
