package store.nightmarket.domain.user.valueobject;

import java.util.EnumSet;
import java.util.Set;

import lombok.Getter;

@Getter
public enum UserRole {

	ROLE_NONE,
	ROLE_ADMIN,
	ROLE_BUYER,
	ROLE_SELLER;

	private Set<UserRole> canTransitionRole;

	static {
		ROLE_NONE.canTransitionRole = EnumSet.of(ROLE_BUYER, ROLE_SELLER);
		ROLE_ADMIN.canTransitionRole = EnumSet.noneOf(UserRole.class);
		ROLE_BUYER.canTransitionRole = EnumSet.noneOf(UserRole.class);
		ROLE_SELLER.canTransitionRole = EnumSet.noneOf(UserRole.class);
	}

	public boolean canTransitionTo(UserRole target) {
		return canTransitionRole.contains(target);
	}

}
