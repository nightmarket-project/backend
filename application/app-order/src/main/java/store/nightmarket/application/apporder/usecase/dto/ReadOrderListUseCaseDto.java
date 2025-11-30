package store.nightmarket.application.apporder.usecase.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.model.id.UserId;

public class ReadOrderListUseCaseDto {

	@Builder
	public record Input(
		UserId userId,
		int page,
		int size
	) {

	}

	@Builder
	public record Output(
		Page<OrderRecord> orderRecords
	) {

	}

}
