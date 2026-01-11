package store.nightmarket.domain.payment.util;

import java.util.List;
import java.util.UUID;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.model.Product;
import store.nightmarket.domain.payment.model.id.DetailPaymentRecordId;
import store.nightmarket.domain.payment.model.id.OrderId;
import store.nightmarket.domain.payment.model.id.PaymentRecordId;
import store.nightmarket.domain.payment.model.id.ProductId;
import store.nightmarket.domain.payment.model.id.UserId;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.valueobject.Price;

public class PaymentTestUtil {

	public static PaymentRecord createTestPayment(
		UUID recordId,
		UUID userId,
		UUID orderId,
		List<DetailPaymentRecord> details
	) {
		return PaymentRecord.newInstance(
			new PaymentRecordId(recordId),
			new UserId(userId),
			new OrderId(orderId),
			details
		);
	}

	public static DetailPaymentRecord createTestDetailPayment(
		UUID detailRecordId,
		Product product,
		DetailPaymentState state
	) {
		return DetailPaymentRecord.newInstance(
			new DetailPaymentRecordId(detailRecordId),
			product,
			state
		);
	}

	public static Product createTestProduct(
		UUID productId,
		long price
	) {
		return Product.newInstance(
			new ProductId(productId),
			new Price(price)
		);
	}

}
