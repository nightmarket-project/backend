package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

public class ModifyProductDto {

	@Builder
	public record Request(
		String name,
		String description,
		int price
	) {

	}

}
