package store.nightmarket.application.apporder.usecase;

import static store.nightmarket.application.apporder.usecase.dto.ReadOrderListUseCaseDto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.out.ReadOrderPort;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.order.model.OrderRecord;

@Service
@RequiredArgsConstructor
public class ReadOrderListUseCase implements BaseUseCase<Input, Output> {

	private final ReadOrderPort readOrderPort;

	@Override
	public Output execute(Input input) {
		Pageable pageable = PageRequest.of(input.page(), input.size());

		Page<OrderRecord> orderRecords = readOrderPort.readAllByUserId(input.userId(), pageable);

		return Output.builder()
			.orderRecords(orderRecords)
			.build();
	}

}
