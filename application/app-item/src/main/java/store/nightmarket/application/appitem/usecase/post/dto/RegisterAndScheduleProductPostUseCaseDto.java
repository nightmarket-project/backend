package store.nightmarket.application.appitem.usecase.post.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;

public class RegisterAndScheduleProductPostUseCaseDto {

	@Builder
	public record Input(
		ProductId productId,
		UserId userId,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {

	}

}
