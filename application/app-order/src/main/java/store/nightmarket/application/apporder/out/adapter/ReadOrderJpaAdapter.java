package store.nightmarket.application.apporder.out.adapter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.mapper.OrderRecordMapper;
import store.nightmarket.application.apporder.out.ReadOrderPort;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.persistence.persistorder.repository.OrderRecordRepository;

@Component
@RequiredArgsConstructor
public class ReadOrderJpaAdapter implements ReadOrderPort {

	private OrderRecordRepository orderRecordRepository;

	@Override
	public Optional<OrderRecord> read(OrderRecordId id) {
		return orderRecordRepository.findByOrderRecordId(id.getId())
			.map(OrderRecordMapper::toDomain);
	}

}
