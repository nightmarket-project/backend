package store.nightmarket.application.appuser.out.adaptor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.out.ReadUserPort;
import store.nightmarket.application.appuser.out.mapper.UserMapper;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.persistence.persistuser.repository.UserEntityRepository;

@Component
@RequiredArgsConstructor
public class ReadUserJpaAdapter implements ReadUserPort {

	private final UserEntityRepository userEntityRepository;

	@Override
	public Optional<User> read(UUID id) {
		return userEntityRepository.findById(id)
			.map(UserMapper::toDomain);
	}

	@Override
	public Optional<User> readByEmail(String email) {
		return userEntityRepository.findByEmail(email)
			.map(UserMapper::toDomain);
	}

}
