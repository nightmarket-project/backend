package store.nightmarket.domain.order.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;

public class CancelDetailOrderDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private final OrderRecord orderRecord;
		private final DetailOrderRecord detailOrderRecord;

	}

	@Getter
	@Builder
	public static class Event {

		private final OrderRecord orderRecord;

	}

}
