package store.nightmarket.application.apporder.in.dto;

import lombok.Builder;
import store.nightmarket.domain.order.model.OrderRecord;

public class ReadOrderDto {

	@Builder
	public record Response(
		OrderRecord orderRecord
	) {

	}
}
