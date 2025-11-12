package store.nightmarket.application.apppayment.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentRequestEvent(
	UUID orderId,
	UUID userId,
	long totalPrice,
	List<PaymentItem> paymentItems
) {
	@Builder
	public record PaymentItem(
		UUID productId,
		int quantity
	) {

	}
}
