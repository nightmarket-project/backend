package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;
import java.util.UUID;

import lombok.Builder;

public class RegisterProductDto {

	@Builder
	public record Request(
		String name,
		String description,
		BigInteger price
	) {

	}

	@Builder
	public record Response(
		UUID productId
	) {

	}

}
