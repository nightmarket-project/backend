package store.nightmarket.application.apporder.in.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadOrderDto {

	@Builder
	public record Response(
		UUID id,
		UUID userId,
		LocalDate orderDate,
		Address address,
		List<DetailOrderRecord> detailOrderRecordList
	) {

	}

	@Builder
	public record Address(
		String zipCode,
		String roadAddress,
		String detailAddress
	) {

	}

	@Builder
	public record DetailOrderRecord(
		UUID detailOrderRecordId,
		UUID productVariantId,
		Integer quantity,
		String state
	) {

	}

}
