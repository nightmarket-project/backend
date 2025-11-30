package store.nightmarket.application.apporder.in;

import static store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto.*;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.auth.AuthorizedUser;
import store.nightmarket.application.apporder.auth.UserSession;
import store.nightmarket.application.apporder.in.dto.ReadListOrderDto;
import store.nightmarket.application.apporder.in.dto.ReadOrderDto;
import store.nightmarket.application.apporder.in.dto.SaveOrderDto;
import store.nightmarket.application.apporder.usecase.ReadOrderListUseCase;
import store.nightmarket.application.apporder.usecase.ReadOrderUseCase;
import store.nightmarket.application.apporder.usecase.RequestOrderUseCase;
import store.nightmarket.application.apporder.usecase.dto.ReadOrderListUseCaseDto;
import store.nightmarket.application.apporder.usecase.dto.ReadOrderUseCaseDto;
import store.nightmarket.domain.order.model.id.OrderRecordId;
import store.nightmarket.domain.order.model.id.UserId;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderControllerV1 {

	private final RequestOrderUseCase requestOrderUseCase;
	private final ReadOrderUseCase readOrderUseCase;
	private final ReadOrderListUseCase readOrderListUseCase;

	@GetMapping()
	public ReadListOrderDto.Response readOrderList(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "25") int size,
		@AuthorizedUser UserSession userSession
	) {
		ReadOrderListUseCaseDto.Input input = ReadOrderListUseCaseDto.Input.builder()
			.userId(new UserId(UUID.fromString(userSession.userId())))
			.page(page)
			.size(size)
			.build();

		ReadOrderListUseCaseDto.Output output = readOrderListUseCase.execute(input);

		return ReadListOrderDto.Response.builder()
			.contents(
				output.orderRecords().stream()
					.map(orderOutput -> ReadOrderDto.Response.builder()
						.id(orderOutput.getOrderRecordId().getId())
						.orderDate(orderOutput.getOrderDate())
						.address(ReadOrderDto.Address.builder()
							.zipCode(orderOutput.getAddress().getZipCode())
							.roadAddress(orderOutput.getAddress().getRoadAddress())
							.detailAddress(orderOutput.getAddress().getDetailAddress())
							.build())
						.detailOrderRecordList(
							orderOutput.getDetailOrderRecordList().stream()
								.map(detailOutput -> ReadOrderDto.DetailOrderRecord.builder()
									.detailOrderRecordId(detailOutput.getDetailOrderRecordId().getId())
									.productVariantId(detailOutput.getProductVariantId().getId())
									.quantity(detailOutput.getQuantity().getValue())
									.state(detailOutput.getState().name())
									.build())
								.toList()
						)
						.build())
					.toList()
			)
			.currentPage(page)
			.numberOfElements(output.orderRecords().getNumberOfElements())
			.totalPage(output.orderRecords().getTotalPages())
			.totalElements(output.orderRecords().getTotalElements())
			.hasNext(output.orderRecords().hasNext())
			.build();
	}

	@GetMapping("/{orderId}")
	public ReadOrderDto.Response readOrder(@PathVariable("orderId") UUID orderId) {
		ReadOrderUseCaseDto.Output output = readOrderUseCase.execute(new OrderRecordId(orderId));

		return ReadOrderDto.Response.builder()
			.id(output.orderRecord().getOrderRecordId().getId())
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
	public SaveOrderDto.Response saveOrder(
		@RequestBody SaveOrderDto.Request request,
		@AuthorizedUser UserSession userSession
	) {
		Output output = requestOrderUseCase.execute(
			Input.builder()
				.addressDto(
					AddressDto.builder()
						.zipCode(request.addressDto().zipCode())
						.roadAddress(request.addressDto().roadAddress())
						.detailAddress(request.addressDto().detailAddress())
						.build()
				)
				.userId(UUID.fromString(userSession.userId()))
				.detailOrderDtoList(toUseCaseDto(request.detailOrderDtoList()))
				.build()
		);

		return SaveOrderDto.Response.builder()
			.orderRecordId(output.orderRecordId())
			.build();
	}

}
