package store.nightmarket.domain.payment.service.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.model.Product;
import store.nightmarket.domain.payment.valueobject.UserId;

public class RequestPaymentDomainServiceDto {

	@Getter
	@Builder
	public static class Input {

		private final UserId userId;
		private final List<Product> productList;

	}

	@Builder
	public static class Output {

		private final PaymentRecord paymentRecord;

	}

}
