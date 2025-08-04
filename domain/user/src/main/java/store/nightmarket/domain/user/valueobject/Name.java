package store.nightmarket.domain.user.valueobject;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import store.nightmarket.domain.user.exception.UserException;

@Getter
public class Name implements Serializable {

	private final String value;

	public Name(String value) {
		validate(value);
		this.value = value;
	}

	private void validate(String name) {
		if (name == null) {
			throw new UserException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new UserException("Name cannot be blank");
		}
		if (name.length() > 64) {
			throw new UserException("Name cannot be longer than 64 characters");
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Name other = (Name)obj;
		return Objects.equals(value, other.value);
	}

}
