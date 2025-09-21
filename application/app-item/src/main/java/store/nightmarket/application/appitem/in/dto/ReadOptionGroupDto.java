package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.OptionGroupId;
import store.nightmarket.domain.item.valueobject.OptionValueId;

public class ReadOptionGroupDto {

	@Builder
	public record Response(
		List<OptionGroupInfo> optionGroupInfoList
	) {

	}

	@Builder
	public record OptionGroupInfo(
		OptionGroupId optionGroupId,
		String name,
		int displayOrder,
		List<OptionValueInfo> optionValueInfoList
	) {

	}

	@Builder
	public record OptionValueInfo(
		OptionValueId optionValueId,
		String name,
		BigDecimal price,
		int displayOrder
	) {

	}

}
