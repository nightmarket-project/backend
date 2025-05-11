package store.nightmarket.domain.order.valueobject;

import store.nightmarket.domain.order.exception.OrderException;

public class Quantity {

	private final int value;

	public Quantity(int value) {
		if (value <= 0) {
			throw new OrderException("Quantity must be greater than zero.");
		}
		this.value = value;
	}

}
