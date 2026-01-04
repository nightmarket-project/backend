package store.nightmarket.application.appitem.usecase.user.dto;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;

public class SaveUserUseCaseDto {

	@Builder
	public record Input(
		UserId userId,
		Name name
	) {

	}

}
