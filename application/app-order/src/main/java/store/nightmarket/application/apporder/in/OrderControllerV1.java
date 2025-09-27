package store.nightmarket.application.apporder.in;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.in.dto.ReadOrderDto;
import store.nightmarket.application.apporder.in.dto.SaveOrderDto;
import store.nightmarket.application.apporder.usecase.ReadOrderUseCase;
import store.nightmarket.application.apporder.usecase.RequestOrderUseCase;
import store.nightmarket.application.apporder.usecase.dto.ReadOrderUseCaseDto;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.domain.order.valueobject.OrderRecordId;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderControllerV1 {

	private final RequestOrderUseCase requestOrderUseCase;
	private final ReadOrderUseCase readOrderUseCase;

	@GetMapping("/{orderId}")
	public ReadOrderDto.Response readOrder(@PathVariable("orderId") UUID orderId) {
		ReadOrderUseCaseDto.Output output = readOrderUseCase.execute(new OrderRecordId(orderId));

		return ReadOrderDto.Response.builder()
			.id(output.orderRecord().getOrderRecordId().getId())
			.userId(output.orderRecord().getUserId().getId())
			.orderDate(output.orderRecord().getOrderDate())
			.address(
				ReadOrderDto.Address.builder()
					.zipCode(output.orderRecord().getAddress().getZipCode())
					.roadAddress(output.orderRecord().getAddress().getRoadAddress())
					.detailAddress(output.orderRecord().getAddress().getDetailAddress())
					.build()
			)
			.detailOrderRecordList(
				output.orderRecord().getDetailOrderRecordList().stream()
					.map(detailOrderRecord ->
						ReadOrderDto.DetailOrderRecord.builder()
							.detailOrderRecordId(detailOrderRecord.getDetailOrderRecordId().getId())
							.productVariantId(detailOrderRecord.getProductVariantId().getId())
							.quantity(detailOrderRecord.getQuantity().getValue())
							.state(detailOrderRecord.getState().name())
							.build()
					)
					.toList()

			)
			.build();
	}

	@PostMapping
	public void saveOrder(@RequestBody SaveOrderDto.Request request) {
		requestOrderUseCase.execute(
			RequestOrderUseCaseDto.Input.builder()
				.addressDto(
					RequestOrderUseCaseDto.AddressDto.builder()
						.zipCode(request.addressDto().zipCode())
						.roadAddress(request.addressDto().roadAddress())
						.detailAddress(request.addressDto().detailAddress())
						.build()
				)
				.userId(request.userId())
				.build()
		);
	}

}
