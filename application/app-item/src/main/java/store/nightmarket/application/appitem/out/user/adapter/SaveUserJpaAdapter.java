package store.nightmarket.application.appitem.out.user.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.user.mapper.UserMapper;
import store.nightmarket.application.appitem.out.user.SaveUserPort;
import store.nightmarket.domain.item.model.User;
import store.nightmarket.persistence.persistitem.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SaveUserJpaAdapter implements SaveUserPort {

	private final UserRepository userRepository;

	@Override
	public void save(User user) {
		userRepository.save(UserMapper.toEntity(user));
	}

}
