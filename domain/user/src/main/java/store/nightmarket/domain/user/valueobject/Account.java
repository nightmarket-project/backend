package store.nightmarket.domain.user.valueobject;

import java.util.Objects;

import lombok.Getter;
import store.nightmarket.domain.user.exception.UserException;

@Getter
public class Account {

	private final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	private String email;

	public Account(String email) {
		validate(email);
		this.email = email;
	}

	private void validate(String email) {
		if (email == null) {
			throw new UserException("Email cannot be null");
		}
		if (email.isBlank()) {
			throw new UserException("Email cannot be blank");
		}
		if (!email.matches(emailRegex)) {
			throw new UserException("Unmatched Email format");
		}
	}

	public boolean isMatched(Account other) {
		return email.equals(other.getEmail());
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account)o;
		return Objects.equals(email, account.email);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(email);
	}

}
