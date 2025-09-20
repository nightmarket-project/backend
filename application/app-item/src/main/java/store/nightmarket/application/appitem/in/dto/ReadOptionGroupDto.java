package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;
import store.nightmarket.domain.item.valueobject.Price;

public class ReadOptionGroupDto {

	@Builder
	public record Response(
		List<OptionGroupInfo> optionGroupInfoList
	) {

	}

	@Builder
	public record OptionGroupInfo(
		OptionGroupId optionGroupId,
		Name name,
		int displayOrder,
		List<OptionValueInfo> optionValueInfoList
	) {

	}

	@Builder
	public record OptionValueInfo(
		OptionValueId optionValueId,
		Name name,
		Price price,
		int displayOrder
	) {

	}

}
