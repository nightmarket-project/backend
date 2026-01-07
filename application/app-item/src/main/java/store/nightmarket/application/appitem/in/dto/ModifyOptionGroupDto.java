package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;

public class ModifyOptionGroupDto {

	@Builder
	public record Request(
		String name,
		int displayOrder
	) {

	}

}
