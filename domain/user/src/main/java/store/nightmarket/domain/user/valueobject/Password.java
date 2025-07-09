package store.nightmarket.domain.user.valueobject;

import java.util.Objects;

import lombok.Getter;
import store.nightmarket.domain.user.exception.UserException;

@Getter
public class Password {

	private String value;

	public Password(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String value) {
		if (value == null) {
			throw new UserException("Password cannot be null");
		}
		if (value.isBlank()) {
			throw new UserException("Password cannot be blank");
		}
	}

	public boolean isMatched(Password other) {
		return value.equals(other.getValue());
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		Password password = (Password)o;
		return Objects.equals(value, password.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

}
