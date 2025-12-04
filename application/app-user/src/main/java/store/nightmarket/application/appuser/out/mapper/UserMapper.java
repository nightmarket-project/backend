package store.nightmarket.application.appuser.out.mapper;

import store.nightmarket.domain.user.model.User;
import store.nightmarket.domain.user.model.id.UserId;
import store.nightmarket.domain.user.valueobject.Name;
import store.nightmarket.domain.user.valueobject.Point;
import store.nightmarket.persistence.persistuser.entity.model.UserEntity;
import store.nightmarket.persistence.persistuser.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistuser.entity.valueobject.PointEntity;

public class UserMapper {

	public static User toDomain(UserEntity entity) {
		return User.newInstanceWithCreatedAt(
			new UserId(entity.getId()),
			entity.getCreatedAt(),
			new Name(entity.getNameEntity().getName()),
			entity.getEmail(),
			entity.getProfileImageUrl(),
			new Point(entity.getPointEntity().getPoint()),
			entity.getRole(),
			entity.getAuthProvider(),
			entity.getProviderId()
		);
	}

	public static UserEntity toEntity(User domain) {
		return new UserEntity(
			domain.getUserId().getId(),
			domain.getCreatedAt(),
			new NameEntity(domain.getName().getValue()),
			domain.getEmail(),
			domain.getImageUrl(),
			new PointEntity(domain.getPoint().getValue()),
			domain.getRole(),
			domain.getAuthProvider(),
			domain.getProviderId()
		);
	}

}
