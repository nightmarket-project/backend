package store.nightmarket.application.apppayment.out.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record PaymentSuccessEventDto(
	UUID paymentRecordId,
	UUID orderId,
	UUID userId,
	long price
) {

}
