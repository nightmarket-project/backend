package store.nightmarket.application.appuser.out;

import store.nightmarket.domain.user.model.User;

public interface SaveUserPort {

	User save(User user);

}

