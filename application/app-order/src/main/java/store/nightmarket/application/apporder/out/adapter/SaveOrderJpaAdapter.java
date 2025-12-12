package store.nightmarket.application.apporder.out.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.apporder.out.SaveOrderPort;
import store.nightmarket.application.apporder.out.mapper.DetailOrderRecordMapper;
import store.nightmarket.application.apporder.out.mapper.OrderRecordMapper;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.persistence.persistorder.entity.model.OrderRecordEntity;
import store.nightmarket.persistence.persistorder.repository.DetailOrderRecordRepository;
import store.nightmarket.persistence.persistorder.repository.OrderRecordRepository;

@Component
@RequiredArgsConstructor
public class SaveOrderJpaAdapter implements SaveOrderPort {

	private final OrderRecordRepository orderRecordRepository;
	private final DetailOrderRecordRepository detailOrderRecordRepository;

	@Override
	public void save(OrderRecord orderRecord) {
		OrderRecordEntity entity = OrderRecordMapper.toEntity(orderRecord);
		orderRecordRepository.save(entity);
		detailOrderRecordRepository.saveAll(
			DetailOrderRecordMapper.toEntityList(
				orderRecord.getDetailOrderRecordList(),
				entity
			)
		);
	}

}
