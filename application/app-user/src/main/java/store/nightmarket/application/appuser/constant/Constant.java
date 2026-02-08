package store.nightmarket.application.appuser.constant;

public class Constant {
	// auth
	public static final String SESSION_KEY = "SPRING_SECURITY_CONTEXT";
	public static final String USER_SESSION = "USER_SESSION";
	public static final String SESSION_STATE = "session_state";
	public static final String LOGIN_SUCCESS = "success";

	// outbox
	public static final String USER_CREATED_EVENT = "UserCreatedEvent";
	public static final String USER_CREATED_TOPIC = "user.user-created";
}
