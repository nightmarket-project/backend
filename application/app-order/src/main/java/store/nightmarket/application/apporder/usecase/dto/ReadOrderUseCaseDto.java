package store.nightmarket.application.apporder.usecase.dto;

import lombok.Builder;
import store.nightmarket.domain.order.model.OrderRecord;

public class ReadOrderUseCaseDto {

	@Builder
	public record Output(
		OrderRecord orderRecord
	) {

	}

}
