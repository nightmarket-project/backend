package store.nightmarket.application.apppayment.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.payment.model.id.OrderId;
import store.nightmarket.domain.payment.model.id.UserId;

public class SuccessSimplePaymentUseCaseDto {

	@Builder
	public record Input(
		OrderId orderId,
		UserId userId,
		long price
	) {

	}

}
