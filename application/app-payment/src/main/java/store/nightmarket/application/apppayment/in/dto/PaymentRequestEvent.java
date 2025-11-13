package store.nightmarket.application.apppayment.in.dto;

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
		UUID productVariantId,
		long price,
		int quantity
	) {

	}
}
