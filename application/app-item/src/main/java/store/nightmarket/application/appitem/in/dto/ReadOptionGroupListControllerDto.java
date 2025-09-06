package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;

public class ReadOptionGroupListControllerDto {

	@Builder
	public record Response(
		List<OptionGroupControllerDto> optionGroupListControllerDtoList
	) {

	}

	@Builder
	public record OptionGroupControllerDto(
		OptionGroupId optionGroupId,
		Name name,
		int displayOrder,
		List<OptionValueControllerDto> optionValueControllerDtoList
	) {

	}

	@Builder
	public record OptionValueControllerDto(
		OptionValueId optionValueId,
		Name name,
		Price price,
		int displayOrder
	) {

	}

}
