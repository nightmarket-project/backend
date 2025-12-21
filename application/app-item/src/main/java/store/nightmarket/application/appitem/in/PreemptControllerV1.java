package store.nightmarket.application.appitem.in;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.PreemptProductDto;
import store.nightmarket.application.appitem.usecase.PreemptProductUseCase;
import store.nightmarket.application.appitem.usecase.dto.PreemptProductUseCaseDto;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

@RestController
@RequestMapping("api/v1/preempt")
@RequiredArgsConstructor
public class PreemptControllerV1 {

	private final PreemptProductUseCase preemptProductUseCase;

	@PostMapping
	public PreemptProductDto.Response preempt(@RequestBody PreemptProductDto.Request request) {
		PreemptProductUseCaseDto.Input input = PreemptProductUseCaseDto.Input.builder()
			.orderId(new OrderId(request.orderId()))
			.preemptionProductList(
				request.preemptProductList().stream()
					.map(productQuantityDto -> PreemptProductUseCaseDto.PreemptionProduct.builder()
						.productVariantId(new ProductVariantId(productQuantityDto.productVariantId()))
						.quantity(new Quantity(BigInteger.valueOf(productQuantityDto.quantity())))
						.build())
					.toList())
			.build();

		PreemptProductUseCaseDto.Output output = preemptProductUseCase.execute(input);

		return PreemptProductDto.Response.builder()
			.isSuccess(output.isSuccess())
			.insufficientProductList(output.insufficientProductList())
			.build();
	}

}
