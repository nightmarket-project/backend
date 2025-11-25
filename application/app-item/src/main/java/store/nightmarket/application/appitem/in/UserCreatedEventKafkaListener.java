package store.nightmarket.application.appitem.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.appitem.in.dto.UserCreatedEvent;
import store.nightmarket.application.appitem.usecase.SaveUserUseCase;
import store.nightmarket.application.appitem.usecase.dto.SaveUserUseCaseDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedEventKafkaListener {

	private final SaveUserUseCase saveUserUseCase;

	@KafkaListener(topics = "user.user-created", groupId = "item-service", concurrency = "3")
	public void handleEvent(UserCreatedEvent event) {
		log.info("user.user-created event received: {}", event);

		saveUserUseCase.execute(
			SaveUserUseCaseDto.Input.builder()
				.userId(event.userId())
				.name(event.name())
				.build()
		);
	}

}
