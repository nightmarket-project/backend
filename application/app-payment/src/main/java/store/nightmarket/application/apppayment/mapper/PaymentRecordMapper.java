package store.nightmarket.application.apppayment.mapper;

import java.util.ArrayList;
import java.util.List;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.valueobject.PaymentRecordId;
import store.nightmarket.domain.payment.valueobject.UserId;
import store.nightmarket.persistence.persistpayment.entity.model.DetailPaymentRecordEntity;
import store.nightmarket.persistence.persistpayment.entity.model.PaymentRecordEntity;

public class PaymentRecordMapper {

	public static PaymentRecord toDomain(PaymentRecordEntity entity) {
		List<DetailPaymentRecord> detailPaymentRecords = DetailPaymentRecordMapper
			.toDomainList(entity.getDetailPaymentRecordEntityList());

		return PaymentRecord.newInstance(
			new PaymentRecordId(entity.getId()),
			new UserId(entity.getUserId()),
			detailPaymentRecords
		);
	}

	public static PaymentRecordEntity toEntity(PaymentRecord domain) {
		PaymentRecordEntity entity = new PaymentRecordEntity(
			domain.getUserId().getId(),
			domain.getUserId().getId(),
			new ArrayList<>()
		);

		List<DetailPaymentRecordEntity> entityList = DetailPaymentRecordMapper
			.toEntityList(domain.getDetailPaymentRecordList(), entity);

		entity.getDetailPaymentRecordEntityList().addAll(entityList);

		return entity;
	}

}
