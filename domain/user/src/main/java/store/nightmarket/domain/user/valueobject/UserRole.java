package store.nightmarket.domain.user.valueobject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

	ADMIN("ROLE_ADMIN", "관리자"),
	BUYER("ROLE_BUYER", "구매자"),
	SELLER("ROLE_SELLER", "판매자");

	private final String key;
	private final String title;

}
