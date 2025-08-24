package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record OptionValueControllerDto(
	UUID OptionGroupId,
	String value,
	BigDecimal price,
	int displayOrder
) {

}
