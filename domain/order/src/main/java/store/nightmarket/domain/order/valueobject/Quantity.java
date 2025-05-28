package store.nightmarket.domain.order.valueobject;

import lombok.Getter;
import store.nightmarket.domain.order.exception.OrderException;

@Getter
public class Quantity {

	private final int value;

	public Quantity(int value) {
		if (value <= 0) {
			throw new OrderException("Quantity must be greater than zero.");
		}
		this.value = value;
	}

}
