package store.nightmarket.application.apporder.usecase;

import static store.nightmarket.application.apporder.out.feign.PreemptApiDto.*;
import static store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.nightmarket.application.apporder.out.ReadProductVariantPort;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.out.adapter.PaymentRequestEventKafkaPublisher;
import store.nightmarket.application.apporder.out.dto.PaymentRequestEvent;
import store.nightmarket.application.apporder.out.feign.PreemptApiCaller;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.domain.order.model.id.DetailOrderRecordId;
import store.nightmarket.domain.order.model.id.OrderRecordId;
import store.nightmarket.domain.order.model.id.ProductVariantId;
import store.nightmarket.domain.order.model.id.UserId;
import store.nightmarket.domain.order.model.status.DetailOrderState;
import store.nightmarket.domain.order.service.RequestOrderDomainService;
import store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.valueobject.Quantity;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestOrderUseCase implements BaseUseCase<Input, Output> {

	private final SaveOrderPort saveOrderPort;
	private final ReadProductVariantPort readProductVariantPort;
	private final RequestOrderDomainService requestOrderDomainService;
	private final PreemptApiCaller preemptApiCaller;
	private final PaymentRequestEventKafkaPublisher paymentRequestEventKafkaPublisher;

	@Override
	@Transactional
	public Output execute(Input input) {
		OrderRecord orderRecord = makeOrderRecord(input);

		RequestOrderDomainServiceDto.Event event = requestOrderDomainService.execute(
			RequestOrderDomainServiceDto.Input.builder()
				.orderRecord(orderRecord)
				.build()
		);

		OrderRecord submittedOrderRecord = event.getOrderRecord();

		saveOrderPort.save(submittedOrderRecord);

		PreemptResponse preemptResponse = preempt(submittedOrderRecord);

		if (preemptResponse.isSuccess()) {
			paymentRequestEventKafkaPublisher.publishEvent(
				PaymentRequestEvent.builder()
					.orderId(submittedOrderRecord.getOrderRecordId().getId())
					.userId(submittedOrderRecord.getUserId().getId())
					.price(calculatePrice(submittedOrderRecord))
					.build()
			);
		}

		return Output.builder()
			.orderRecordId(submittedOrderRecord.getOrderRecordId().getId())
			.insufficientProductList(preemptResponse.insufficientProductList())
			.build();
	}

	private OrderRecord makeOrderRecord(Input input) {
		return OrderRecord.newInstance(
			new OrderRecordId(UUID.randomUUID()),
			new Address(
				input.addressDto().zipCode(),
				input.addressDto().roadAddress(),
				input.addressDto().detailAddress()
			),
			LocalDate.now(),
			new UserId(input.userId()),
			input.detailOrderDtoList().stream()
				.map(dto -> DetailOrderRecord.newInstance(
					new DetailOrderRecordId(UUID.randomUUID()),
					new ProductVariantId(dto.productVariantId().getId()),
					new Quantity(dto.quantity().getValue()),
					DetailOrderState.NONE
				))
				.toList()
		);
	}

	public PreemptResponse preempt(OrderRecord submittedOrderRecord) {
		try {
			return preemptApiCaller.preemptRequest(buildRequest(submittedOrderRecord));
		} catch (FeignException e) {
			log.error("Preempt API 호출 실패. orderId={}", submittedOrderRecord.getOrderRecordId(), e);
			throw new OrderException("Preempt API Call Fail");
		}
	}

	private PreemptRequest buildRequest(OrderRecord submittedOrderRecord) {
		return PreemptRequest.builder()
			.orderId(submittedOrderRecord.getOrderRecordId().getId())
			.preemptRequestedProductList(
				submittedOrderRecord.getDetailOrderRecordList().stream()
					.map(detailOrderRecord ->
						PreemptRequest.PreemptRequestedProduct.builder()
							.productVariantId(detailOrderRecord.getProductVariantId().getId())
							.quantity(detailOrderRecord.getQuantity().getValue())
							.build()
					)
					.toList()
			)
			.build();
	}

	private long calculatePrice(OrderRecord orderRecord) {
		List<ProductVariantId> productVariantIdList = orderRecord.getDetailOrderRecordList()
			.stream()
			.map(DetailOrderRecord::getProductVariantId)
			.toList();

		List<ProductVariant> productVariantList = readProductVariantPort.readByIdList(productVariantIdList);

		Map<UUID, Integer> productVariantQuantityMap = orderRecord.getDetailOrderRecordList()
			.stream()
			.collect(Collectors.toMap(
				d -> d.getProductVariantId().getId(),
				d -> d.getQuantity().getValue()
			));

		return productVariantList.stream()
			.mapToLong(productVariant -> productVariantQuantityMap.get(productVariant.getProductVariantId().getId())
				* productVariant.getPrice().getPrice())
			.sum();
	}

}
