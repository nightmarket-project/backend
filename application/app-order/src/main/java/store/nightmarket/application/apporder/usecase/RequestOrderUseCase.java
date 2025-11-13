package store.nightmarket.application.apporder.usecase;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.in.dto.PaymentRequestEvent;
import store.nightmarket.application.apporder.out.ReadProductVariantPort;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.out.adapter.PaymentRequestEventKafkaPublisher;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.domain.order.service.RequestOrderDomainService;
import store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductVariantId;
import store.nightmarket.domain.order.valueobject.Quantity;
import store.nightmarket.domain.order.valueobject.UserId;

@Service
@RequiredArgsConstructor
public class RequestOrderUseCase implements BaseUseCase<RequestOrderUseCaseDto.Input, Void> {

	private final SaveOrderPort saveOrderPort;
	private final ReadProductVariantPort readProductVariantPort;
	private final RequestOrderDomainService requestOrderDomainService;
	private final PaymentRequestEventKafkaPublisher paymentRequestEventKafkaPublisher;

	@Override
	@Transactional
	public Void execute(RequestOrderUseCaseDto.Input input) {
		OrderRecord orderRecord = OrderRecord.newInstance(
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

		RequestOrderDomainServiceDto.Input domainInput = RequestOrderDomainServiceDto.Input.builder()
			.orderRecord(orderRecord)
			.build();

		RequestOrderDomainServiceDto.Event event = requestOrderDomainService.execute(domainInput);

		OrderRecord submittedOrderRecord = event.getOrderRecord();

		saveOrderPort.save(submittedOrderRecord);

		List<ProductVariantId> productVariantIdList = submittedOrderRecord.getDetailOrderRecordList()
			.stream()
			.map(DetailOrderRecord::getProductVariantId)
			.toList();

		List<ProductVariant> productVariantList = readProductVariantPort.readByIdList(productVariantIdList);

		Map<UUID, Integer> productVariantQuantityMap = submittedOrderRecord.getDetailOrderRecordList()
			.stream()
			.collect(Collectors.toMap(
				d -> d.getProductVariantId().getId(),
				d -> d.getQuantity().getValue()
			));

		paymentRequestEventKafkaPublisher.publishEvent(
			PaymentRequestEvent.builder()
				.orderId(submittedOrderRecord.getOrderRecordId().getId())
				.userId(submittedOrderRecord.getUserId().getId())
				.paymentItems(
					productVariantList.stream()
						.map(productVariant -> PaymentRequestEvent.PaymentItem.builder()
							.productVariantId(productVariant.getProductVariantId().getId())
							.price(productVariant.getPrice().getPrice())
							.quantity(
								Optional.ofNullable(
									productVariantQuantityMap.get(productVariant.getProductVariantId().getId())
								).orElseThrow(() ->
									new OrderException("No quantity found for productVariantId: " +
										productVariant.getProductVariantId().getId())
								)
							)
							.build()
						)
						.toList()
				)
				.build()
		);

		return null;
	}

}
