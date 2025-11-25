package store.nightmarket.application.appitem.auth;

import java.util.List;

public record SecurityContext(
	Authentication authentication
) {

	public record Authentication(
		List<Authority> authorities,
		Principal principal
	) {

		public record Authority(
			String authority
		) {
		}

		public record Principal(
			String userId,
			String userName
		) {
		}

	}

}





