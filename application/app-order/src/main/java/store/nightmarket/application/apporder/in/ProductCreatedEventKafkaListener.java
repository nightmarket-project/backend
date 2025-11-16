package store.nightmarket.application.apporder.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.apporder.in.dto.ProductCreatedEvent;
import store.nightmarket.application.apporder.usecase.SaveProductVariantUseCase;
import store.nightmarket.application.apporder.usecase.dto.SaveProductVariantUseCaseDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreatedEventKafkaListener {

	private final SaveProductVariantUseCase saveProductVariantUseCase;

	@KafkaListener(topics = "item.product-created", groupId = "order-service", concurrency = "3")
	public void handleEvent(ProductCreatedEvent event) {
		log.info("item.product-created event received: {}", event);

		saveProductVariantUseCase.execute(
			SaveProductVariantUseCaseDto.Input.builder()
				.productVariantId(event.id())
				.price(event.price())
				.build()
		);
	}

}
