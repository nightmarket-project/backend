package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;

public class ReadProductPostDto {

	@Builder
	public record Response(
		ProductPostControllerDto productPostControllerDto,
		List<ReviewReplyControllerDto> reviewReplyControllerDtoList,
		List<OptionGroupValueControllerDto> optionGroupValueControllerDtoList,
		List<OptionLayerDto> optionLayerDtoList
	) {

	}

}
