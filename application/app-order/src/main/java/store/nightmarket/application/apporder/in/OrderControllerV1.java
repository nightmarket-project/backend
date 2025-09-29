package store.nightmarket.application.apporder.in;

import static store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.in.dto.SaveOrderDto;
import store.nightmarket.application.apporder.usecase.RequestOrderUseCase;
import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderControllerV1 {

	private final RequestOrderUseCase requestOrderUseCase;

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
				.detailOrderDtoList(toUseCaseDto(request.detailOrderDtoList()))
				.build()
		);
	}

}
