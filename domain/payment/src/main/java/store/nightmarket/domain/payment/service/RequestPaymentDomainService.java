package store.nightmarket.domain.payment.service;

import static store.nightmarket.domain.payment.service.dto.RequestPaymentDomainServiceDto.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import store.nightmarket.common.domain.service.BaseDomainService;
import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.model.Product;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.valueobject.DetailPaymentRecordId;
import store.nightmarket.domain.payment.valueobject.PaymentRecordId;
import store.nightmarket.domain.payment.valueobject.UserId;

@Component
public class RequestPaymentDomainService
	implements BaseDomainService<Input, Output> {

	@Override
	public Output execute(Input input) {
		UserId userId = input.getUserId();
		List<Product> productList = input.getProductList();

		List<DetailPaymentRecord> detailPaymentRecordList = productList.stream()
			.map(product -> DetailPaymentRecord.newInstance(
				new DetailPaymentRecordId(UUID.randomUUID()),
				product,
				DetailPaymentState.SUBMITTED
			))
			.collect(Collectors.toList());

		PaymentRecord paymentRecord = PaymentRecord.newInstance(
			new PaymentRecordId(UUID.randomUUID()),
			userId,
			detailPaymentRecordList
		);

		return Output.builder()
			.paymentRecord(paymentRecord)
			.build();
	}

}
