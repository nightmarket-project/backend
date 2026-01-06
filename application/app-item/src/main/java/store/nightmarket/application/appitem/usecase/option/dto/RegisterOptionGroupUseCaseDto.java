package store.nightmarket.application.appitem.usecase.option.dto;

import java.util.List;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterOptionGroupUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		UserId userId,
		Name name,
		int displayOrder,
		List<OptionValueDto> optionValueDtoList
	) {

	}

	@Builder
	public record OptionValueDto(
		Name name,
		Price price,
		int displayOrder
	) {

	}

}
