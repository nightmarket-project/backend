package store.nightmarket.application.apporder.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentRequestEvent(
	UUID orderId,
	UUID userId,
	List<PaymentItem> paymentItems
) {
	@Builder
	public record PaymentItem(
		UUID productId,
		int quantity
	) {

	}
}


