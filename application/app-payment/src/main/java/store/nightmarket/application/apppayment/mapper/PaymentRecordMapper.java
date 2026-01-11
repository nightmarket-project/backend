package store.nightmarket.application.apppayment.mapper;

import java.util.ArrayList;
import java.util.List;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.model.id.OrderId;
import store.nightmarket.domain.payment.model.id.PaymentRecordId;
import store.nightmarket.domain.payment.model.id.UserId;
import store.nightmarket.persistence.persistpayment.entity.model.DetailPaymentRecordEntity;
import store.nightmarket.persistence.persistpayment.entity.model.PaymentRecordEntity;

public class PaymentRecordMapper {

	public static PaymentRecord toDomain(PaymentRecordEntity entity) {
		List<DetailPaymentRecord> detailPaymentRecords = DetailPaymentRecordMapper
			.toDomainList(entity.getDetailPaymentRecordEntityList());

		return PaymentRecord.newInstanceWithCreatedAt(
			new PaymentRecordId(entity.getId()),
			entity.getCreatedAt(),
			new UserId(entity.getUserId()),
			new OrderId(entity.getOrderId()),
			detailPaymentRecords
		);
	}

	public static PaymentRecordEntity toEntity(PaymentRecord domain) {
		PaymentRecordEntity entity = new PaymentRecordEntity(
			domain.getPaymentRecordId().getId(),
			domain.getCreatedAt(),
			domain.getUserId().getId(),
			domain.getOrderId().getId(),
			new ArrayList<>()
		);

		List<DetailPaymentRecordEntity> entityList = DetailPaymentRecordMapper
			.toEntityList(domain.getDetailPaymentRecordList(), entity);

		entity.getDetailPaymentRecordEntityList().addAll(entityList);

		return entity;
	}

}
