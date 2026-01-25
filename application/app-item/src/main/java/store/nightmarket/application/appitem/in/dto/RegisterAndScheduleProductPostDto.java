package store.nightmarket.application.appitem.in.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

public class RegisterAndScheduleProductPostDto {

	@Builder
	public record Request(
		UUID productId,
		LocalDateTime publishAt,
		LocalDateTime expiredAt
	) {

	}

}
