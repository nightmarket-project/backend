package store.nightmarket.application.appitem.in.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class ReadOptionGroupDto {

	@Builder
	public record Response(
		List<OptionGroupInfo> optionGroupInfoList
	) {

	}

	@Builder
	public record OptionGroupInfo(
		UUID optionGroupId,
		String name,
		int displayOrder,
		List<OptionValueInfo> optionValueInfoList
	) {

	}

	@Builder
	public record OptionValueInfo(
		UUID optionValueId,
		String name,
		BigDecimal price,
		int displayOrder
	) {

	}

}
