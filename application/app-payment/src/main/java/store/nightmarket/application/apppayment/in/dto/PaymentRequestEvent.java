package store.nightmarket.application.apppayment.in.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentRequestEvent(
	UUID orderId,
	UUID userId,
	long price
) {

}
