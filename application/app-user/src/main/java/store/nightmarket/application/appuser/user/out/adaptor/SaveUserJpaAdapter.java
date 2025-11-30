package store.nightmarket.application.appuser.user.out.adaptor;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appuser.user.out.SaveUserPort;
import store.nightmarket.domain.user.model.User;
import store.nightmarket.persistence.persistuser.entity.model.UserEntity;
import store.nightmarket.application.appuser.user.out.mapper.UserMapper;
import store.nightmarket.persistence.persistuser.repository.UserEntityRepository;

@Component
@RequiredArgsConstructor
public class SaveUserJpaAdapter implements SaveUserPort {

	private final UserEntityRepository userEntityRepository;

	@Override
	public User save(User user) {
		UserEntity entity = userEntityRepository.save(UserMapper.toEntity(user));
		return UserMapper.toDomain(entity);
	}

}