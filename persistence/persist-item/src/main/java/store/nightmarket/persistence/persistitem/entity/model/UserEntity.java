package store.nightmarket.persistence.persistitem.entity.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.nightmarket.common.entity.BaseUuidEntity;
import store.nightmarket.persistence.persistitem.entity.valueobject.NameEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseUuidEntity {

	@Embedded
	private NameEntity name;

	@OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY)
	private ProductPostEntity productPostEntity;

	@OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
	private List<ReviewEntity> reviewEntityList;

	@OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
	private List<ReplyEntity> replyEntityList;

	public UserEntity(
		UUID id,
		NameEntity name
	) {
		super(id);
		this.name = name;
	}

	public UserEntity(
		UUID id,
		LocalDateTime createdAt,
		NameEntity name
	) {
		super(id, createdAt);
		this.name = name;
	}

	public static UserEntity newInstance(
		UUID id,
		NameEntity name
	) {
		return new UserEntity(
			id,
			name
		);
	}

	public static UserEntity newInstanceWithCreatedAt(
		UUID id,
		LocalDateTime createdAt,
		NameEntity name
	) {
		return new UserEntity(
			id,
			createdAt,
			name
		);
	}

}
