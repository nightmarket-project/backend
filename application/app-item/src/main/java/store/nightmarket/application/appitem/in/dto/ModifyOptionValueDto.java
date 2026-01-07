package store.nightmarket.application.appitem.in.dto;

import java.math.BigInteger;

import lombok.Builder;

public class ModifyOptionValueDto {

	@Builder
	public record Request(
		String name,
		BigInteger price,
		int displayOrder
	) {

	}

}
