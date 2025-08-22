package store.nightmarket.application.apporder.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.mapper.OrderRecordMapper;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.persistence.persistorder.repository.OrderRecordRepository;

@Component
@RequiredArgsConstructor
public class SaveOrderJpaAdapter implements SaveOrderPort {

	private final OrderRecordRepository orderRecordRepository;

	@Override
	public void save(OrderRecord orderRecord) {
		orderRecordRepository.save(OrderRecordMapper.toEntity(orderRecord));
	}

}
