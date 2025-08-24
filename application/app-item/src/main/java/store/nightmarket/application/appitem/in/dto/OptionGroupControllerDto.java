package store.nightmarket.application.appitem.in.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record OptionGroupControllerDto(
	UUID optionGroupId,
	String name,
	int displayOrder
) {

}
