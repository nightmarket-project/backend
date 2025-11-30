package store.nightmarket.application.apppayment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.id.DetailPaymentRecordId;
import store.nightmarket.persistence.persistpayment.entity.model.DetailPaymentRecordEntity;
import store.nightmarket.persistence.persistpayment.entity.model.PaymentRecordEntity;

public class DetailPaymentRecordMapper {

	public static DetailPaymentRecord toDomain(DetailPaymentRecordEntity entity) {
		return DetailPaymentRecord.newInstanceWithCreatedAt(
			new DetailPaymentRecordId(entity.getId()),
			entity.getCreatedAt(),
			ProductMapper.toDomain(entity.getProductEntity()),
			entity.getState()
		);
	}

	public static DetailPaymentRecordEntity toEntity(
		DetailPaymentRecord domain,
		PaymentRecordEntity paymentRecordEntity
	) {
		return new DetailPaymentRecordEntity(
			domain.getDetailPaymentRecordId().getId(),
			domain.getCreatedAt(),
			domain.getState(),
			ProductMapper.toEntity(domain.getProduct()),
			paymentRecordEntity
		);
	}

	public static List<DetailPaymentRecord> toDomainList(List<DetailPaymentRecordEntity> entityList) {
		return entityList
			.stream()
			.map(DetailPaymentRecordMapper::toDomain)
			.collect(Collectors.toList());
	}

	public static List<DetailPaymentRecordEntity> toEntityList(
		List<DetailPaymentRecord> domainList,
		PaymentRecordEntity paymentRecordEntity
	) {
		return domainList
			.stream()
			.map(domain -> toEntity(domain, paymentRecordEntity))
			.collect(Collectors.toList());
	}

}
