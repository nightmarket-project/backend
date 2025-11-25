package store.nightmarket.application.apporder.out.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentRequestEvent(
	UUID orderId,
	UUID userId,
	long price
) {

}


