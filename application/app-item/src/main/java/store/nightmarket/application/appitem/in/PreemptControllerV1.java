package store.nightmarket.application.appitem.in;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.in.dto.PreemptProductVariantDto;
import store.nightmarket.application.appitem.usecase.PreemptProductVariantUseCase;
import store.nightmarket.application.appitem.usecase.dto.PreemptProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.id.OrderId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.valueobject.Quantity;

@RestController
@RequestMapping("api/v1/preempt")
@RequiredArgsConstructor
public class PreemptControllerV1 {

	private final PreemptProductVariantUseCase preemptProductVariantUseCase;

	@PostMapping
	public PreemptProductVariantDto.Response preemptProductVariant(
		@RequestBody PreemptProductVariantDto.Request request) {
		PreemptProductVariantUseCaseDto.Input input = PreemptProductVariantUseCaseDto.Input.builder()
			.orderId(new OrderId(request.orderId()))
			.preemptRequestedProductList(
				request.preemptRequestedProductList().stream()
					.map(productQuantityDto -> PreemptProductVariantUseCaseDto.PreemptRequestedProduct.builder()
						.productVariantId(new ProductVariantId(productQuantityDto.productVariantId()))
						.quantity(new Quantity(BigInteger.valueOf(productQuantityDto.quantity())))
						.build())
					.toList())
			.build();

		PreemptProductVariantUseCaseDto.Output output = preemptProductVariantUseCase.execute(input);

		return PreemptProductVariantDto.Response.builder()
			.isSuccess(output.isSuccess())
			.insufficientProductList(
				output.insufficientProductList().stream()
					.map(ProductVariantId::getId)
					.toList()
			)
			.build();
	}

}
