package store.nightmarket.application.appitem.in.dto;

import lombok.Builder;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.UserId;

@Builder
public record UserResponseDto(
	UserId userId,
	Name name
) {

}
