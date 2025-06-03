package store.nightmarket.application.apppayment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.valueobject.PaymentRecordId;
import store.nightmarket.domain.payment.valueobject.UserId;
import store.nightmarket.persistence.persistpayment.entity.model.DetailPaymentRecordEntity;
import store.nightmarket.persistence.persistpayment.entity.model.PaymentRecordEntity;

public class PaymentRecordMapper {

	public static PaymentRecord toDomain(PaymentRecordEntity entity) {
		List<DetailPaymentRecord> detailPaymentRecordList = entity.getDetailPaymentRecordEntityList()
			.stream()
			.map(DetailPaymentRecordMapper::toDomain)
			.collect(Collectors.toList());

		return PaymentRecord.newInstance(
			new PaymentRecordId(entity.getId()),
			new UserId(entity.getUserId()),
			detailPaymentRecordList
		);
	}

	public static PaymentRecordEntity toEntity(PaymentRecord domain) {
		List<DetailPaymentRecordEntity> detailPaymentRecordEntities = domain.getDetailPaymentRecordList()
			.stream()
			.map(DetailPaymentRecordMapper::toEntity)
			.collect(Collectors.toList());
		return new PaymentRecordEntity(
			domain.getUserId().getId(),
			detailPaymentRecordEntities
		);
	}

}
