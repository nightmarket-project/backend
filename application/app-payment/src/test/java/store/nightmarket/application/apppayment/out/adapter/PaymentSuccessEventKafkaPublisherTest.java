package store.nightmarket.application.apppayment.out.adapter;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import store.nightmarket.application.apppayment.out.dto.PaymentSuccessEventDto;
import store.nightmarket.domain.payment.model.id.OrderId;
import store.nightmarket.domain.payment.model.id.PaymentRecordId;
import store.nightmarket.domain.payment.model.id.UserId;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"order.payment-result"})
class PaymentSuccessEventKafkaPublisherTest {

	@Autowired
	private EmbeddedKafkaBroker embeddedKafkaBroker;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private PaymentSuccessEventKafkaPublisher paymentSuccessEventKafkaPublisher;

	private Consumer<String, PaymentSuccessEventDto> consumer;

	@BeforeEach
	void setUp() {
		Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
			"test-group", "true", embeddedKafkaBroker);

		consumer = new DefaultKafkaConsumerFactory<>(
			consumerProps,
			new StringDeserializer(),
			new JsonDeserializer<>(PaymentSuccessEventDto.class))
			.createConsumer();
		embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "order.payment-result");
	}

	@AfterEach
	void tearDown() {
		consumer.close();
	}

	@Test
	@DisplayName("결제 성공 이벤트를 Kafka에 발행하고 정상 수신한다")
	void publishPaymentSuccessEvent() {
		// given
		PaymentRecordId paymentRecordId = new PaymentRecordId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());
		OrderId orderId = new OrderId(UUID.randomUUID());

		PaymentSuccessEventDto event = PaymentSuccessEventDto.builder()
			.paymentRecordId(paymentRecordId.getId())
			.orderId(orderId.getId())
			.userId(userId.getId())
			.price(1000L)
			.build();

		// when
		paymentSuccessEventKafkaPublisher.publishEvent(event);

		// then
		ConsumerRecord<String, PaymentSuccessEventDto> singleRecord =
			KafkaTestUtils.getSingleRecord(consumer, "order.payment-result");

		assertThat(singleRecord).isNotNull();
		assertThat(singleRecord.key()).isEqualTo(paymentRecordId.toString());
		assertThat(singleRecord.value()).isInstanceOf(PaymentSuccessEventDto.class);
		assertThat(singleRecord.topic()).isEqualTo("order.payment-result");
	}

}
