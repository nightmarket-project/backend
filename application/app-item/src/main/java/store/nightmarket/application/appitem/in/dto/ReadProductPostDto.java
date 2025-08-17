package store.nightmarket.application.appitem.in.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.application.appitem.out.dto.OptionGroupDto;
import store.nightmarket.application.appitem.out.dto.ProductPostDto;
import store.nightmarket.application.appitem.out.dto.ProductVariantDto;

public class ReadProductPostDto {

	@Builder
	public record Response(
		ProductPostDto productPostDto,
		List<OptionGroupDto> optionGroupDtoList,
		List<ProductVariantDto> productVariantDtoList
	) {

	}

}
