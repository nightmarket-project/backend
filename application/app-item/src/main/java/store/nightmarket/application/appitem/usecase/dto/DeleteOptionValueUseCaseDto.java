package store.nightmarket.application.appitem.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteOptionValueUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		OptionGroupId optionGroupId,
		OptionValueId optionValueId,
		UserId userId
	) {

	}

}
