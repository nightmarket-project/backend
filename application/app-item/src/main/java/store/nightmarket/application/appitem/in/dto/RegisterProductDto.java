package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;

import lombok.Builder;

public class RegisterProductDto {

	@Builder
	public record Request(
		String name,
		String description,
		BigInteger price
	) {

	}

}
