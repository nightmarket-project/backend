package store.nightmarket.domain.user.valueobject;

import store.nightmarket.domain.user.exception.UserException;

public class Point {

	private Long value;

	public Point(Long value) {
		validate(value);
		this.value = value;
	}

	public void addPoint(Long value) {
		this.value += value;
	}

	public void subtractPoint(Long value) {
		validate(this.value - value);
		this.value -= value;
	}

	private void validate(Long value) {
		if (value < 0) {
			throw new UserException("User Point cannot be minus");
		}
	}

}
