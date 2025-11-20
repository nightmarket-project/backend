package store.nightmarket.application.apporder.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import store.nightmarket.application.apporder.out.dto.PaymentRequestEvent;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"order.payment-request"})
@DirtiesContext
class PaymentRequestEventTest {

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private Consumer<String, PaymentRequestEvent> consumer;

	@BeforeEach
	void setUp() {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
			"test-group", "true", embeddedKafkaBroker);
		consumer = new DefaultKafkaConsumerFactory<>(
			consumerProps, new StringDeserializer(), new JsonDeserializer<>(PaymentRequestEvent.class))
			.createConsumer();
		embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "order.payment-request");
	}

	@AfterEach
	void tearDown() {
		consumer.close();
	}

	@Test
	void publishAndConsumePaymentRequestEvent() {
		// given
		PaymentRequestEvent event = PaymentRequestEvent.builder()
			.orderId(UUID.randomUUID())
			.userId(UUID.randomUUID())
			.price(10000)
			.build();

		// when
		kafkaTemplate.send("order.payment-request", event.orderId().toString(), event);

		// then
		ConsumerRecord<String, PaymentRequestEvent> singleRecord =
			KafkaTestUtils.getSingleRecord(consumer, "order.payment-request");

		assertThat(singleRecord).isNotNull();
		assertThat(singleRecord.value()).isInstanceOf(PaymentRequestEvent.class); // 직렬화 확인
	}

}
