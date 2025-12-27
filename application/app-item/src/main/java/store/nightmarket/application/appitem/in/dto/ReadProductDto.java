package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.UUID;

import lombok.Builder;

public class ReadProductDto {

	@Builder
	public record Response(
		UUID productId,
		String name,
		String description,
		BigInteger price
	) {

	}

}
