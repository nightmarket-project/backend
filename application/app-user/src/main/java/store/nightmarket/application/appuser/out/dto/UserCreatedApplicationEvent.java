package store.nightmarket.application.appuser.out.dto;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class UserCreatedApplicationEvent extends ApplicationEvent {

	private final UserCreatedEvent payload;

	public UserCreatedApplicationEvent(Object source, UserCreatedEvent payload) {
		super(source);
		this.payload = payload;
	}

}