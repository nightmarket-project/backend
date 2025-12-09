package store.nightmarket.persistence.persistuser.entity.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.domain.user.model.AuthProvider;
import store.nightmarket.domain.user.model.UserRole;
import store.nightmarket.persistence.persistuser.entity.valueobject.NameEntity;
import store.nightmarket.persistence.persistuser.entity.valueobject.PointEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseUuidEntity {

	@Embedded
	@Column(name = "name", nullable = false)
	private NameEntity nameEntity;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "profile_image_url")
	private String profileImageUrl;

	@Embedded
	@Column(name = "point", nullable = false)
	private PointEntity pointEntity;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private UserRole role;

	@Enumerated(EnumType.STRING)
	@Column(name = "auth_provider", nullable = false)
	private AuthProvider authProvider;

	@Column(name = "provider_id")
	private String providerId;

	public UserEntity(
		UUID id,
		NameEntity nameEntity,
		String email,
		String profileImageUrl,
		PointEntity pointEntity,
		UserRole role,
		AuthProvider authProvider,
		String providerId
	) {
		super(id);
		this.nameEntity = nameEntity;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.pointEntity = pointEntity;
		this.role = role;
		this.authProvider = authProvider;
		this.providerId = providerId;
	}

	public UserEntity(
		UUID id,
		LocalDateTime createdAt,
		NameEntity nameEntity,
		String email,
		String profileImageUrl,
		PointEntity pointEntity,
		UserRole role,
		AuthProvider authProvider,
		String providerId
	) {
		super(id, createdAt);
		this.nameEntity = nameEntity;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.pointEntity = pointEntity;
		this.role = role;
		this.authProvider = authProvider;
		this.providerId = providerId;
	}

}
