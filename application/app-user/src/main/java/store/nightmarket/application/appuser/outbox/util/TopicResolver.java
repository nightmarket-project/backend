package store.nightmarket.application.appuser.outbox.util;

import static store.nightmarket.application.appuser.constant.Constant.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import store.nightmarket.domain.user.model.Outbox;

@Component
public class TopicResolver {

	private final Map<String, String> topics = new HashMap<>();

	public TopicResolver() {
		topics.put(USER_CREATED_EVENT, USER_CREATED_TOPIC);
	}

	public String resolve(Outbox outbox) {
		return topics.get(outbox.getEventType());
	}

}