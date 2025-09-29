package store.nightmarket.application.apporder.usecase;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
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
	private final RequestOrderDomainService requestOrderDomainService;

	@Override
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

		saveOrderPort.save(event.getOrderRecord());
		return null;
	}

}
