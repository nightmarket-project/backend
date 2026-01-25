package store.nightmarket.domain.itemweb.service.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.itemweb.model.ProductPost;

public class ScheduleProductPostDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private ProductPost productPost;
		private LocalDateTime publishAt;
		private LocalDateTime expiredAt;

	}

	@Getter
	@Builder
	public static class Event {

		private ProductPost productPost;

	}

}
