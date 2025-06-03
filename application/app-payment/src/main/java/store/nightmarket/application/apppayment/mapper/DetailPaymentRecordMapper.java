package store.nightmarket.application.apppayment.mapper;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.valueobject.DetailPaymentRecordId;
import store.nightmarket.persistence.persistpayment.entity.model.DetailPaymentRecordEntity;

public class DetailPaymentRecordMapper {

	public static DetailPaymentRecord toDomain(DetailPaymentRecordEntity entity) {
		return DetailPaymentRecord.newInstance(
			new DetailPaymentRecordId(entity.getId()),
			ProductMapper.toDomain(entity.getProductEntity()),
			entity.getState()
		);
	}

	public static DetailPaymentRecordEntity toEntity(DetailPaymentRecord domain) {
		return new DetailPaymentRecordEntity(
			ProductMapper.toEntity(domain.getProduct()),
			domain.getState()
		);
	}

}
