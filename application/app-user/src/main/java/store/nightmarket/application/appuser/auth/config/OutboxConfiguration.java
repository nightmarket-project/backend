package store.nightmarket.application.appuser.auth.config;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.nightmarket.application.appoutbox.out.ReadOutboxPort;
import store.nightmarket.application.appoutbox.out.SaveOutboxPort;
import store.nightmarket.application.appoutbox.service.KafkaMessagePublisher;
import store.nightmarket.application.appoutbox.service.OutboxChunkProcessor;
import store.nightmarket.application.appoutbox.strategy.DefaultOutboxEventRegistry;
import store.nightmarket.application.appoutbox.strategy.OutboxEventRegistry;
import store.nightmarket.application.appuser.constant.Constant;
import store.nightmarket.application.appuser.out.dto.UserCreatedEvent;
import store.nightmarket.application.appuser.outbox.UserOutboxEventType;

@Configuration
public class OutboxConfiguration {

	@Bean
	public KafkaMessagePublisher kafkaMessagePublisher(
		KafkaTemplate<String, Object> kafkaTemplate,
		ObjectMapper objectMapper,
		OutboxEventRegistry outboxEventRegistry
	) {
		return new KafkaMessagePublisher(kafkaTemplate, objectMapper, outboxEventRegistry);
	}

	@Bean
	public OutboxChunkProcessor outboxProcessor(
		ReadOutboxPort readOutboxPort,
		SaveOutboxPort saveOutboxPort,
		KafkaMessagePublisher kafkaMessagePublisher
	) {
		return new OutboxChunkProcessor(readOutboxPort, saveOutboxPort, kafkaMessagePublisher);
	}

	@Bean
	public OutboxEventRegistry outboxEventRegistry() {
		return new DefaultOutboxEventRegistry(
			Map.of(UserOutboxEventType.USER_CREATED_EVENT.getType(), Constant.USER_CREATED_TOPIC),
			Map.of(UserOutboxEventType.USER_CREATED_EVENT.getType(), UserCreatedEvent.class)
		);
	}

}
