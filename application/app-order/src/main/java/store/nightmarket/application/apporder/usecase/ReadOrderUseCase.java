package store.nightmarket.application.apporder.usecase;

import static store.nightmarket.application.apporder.usecase.dto.ReadOrderUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.out.ReadOrderPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.model.id.OrderRecordId;

@Service
@RequiredArgsConstructor
public class ReadOrderUseCase implements BaseUseCase<OrderRecordId, Output> {

	private final ReadOrderPort readOrderPort;

	@Override
	public Output execute(OrderRecordId orderRecordId) {
		OrderRecord orderRecord = readOrderPort.readOrThrow(orderRecordId);

		return Output.builder().
			orderRecord(orderRecord)
			.build();
	}

}
