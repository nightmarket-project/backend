package store.nightmarket.application.appuser.user.out;

import java.util.Optional;
import java.util.UUID;

import store.nightmarket.domain.user.exception.UserException;
import store.nightmarket.domain.user.model.User;

public interface ReadUserPort {

	Optional<User> read(UUID id);

	default User readOrThrow(UUID id) {
		return read(id)
			.orElseThrow(() -> new UserException("Not Found User"));
	}

	Optional<User> readByEmail(String email);

}
