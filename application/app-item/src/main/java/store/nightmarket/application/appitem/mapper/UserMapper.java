package store.nightmarket.application.appitem.mapper;

import store.nightmarket.domain.item.model.User;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.UserId;
import store.nightmarket.persistence.persistitem.entity.model.UserEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

public class UserMapper {

	public static User toDomain(UserEntity entity) {
		return User.newInstance(
			new UserId(entity.getId()),
			new Name(entity.getName().getName())
		);
	}

	public static UserEntity toEntity(User user) {
		return UserEntity.newInstance(
			user.getUserId().getId(),
			new NameEntity(user.getName().getValue())
		);
	}
	
}
