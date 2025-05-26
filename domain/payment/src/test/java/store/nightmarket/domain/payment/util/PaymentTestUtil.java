package store.nightmarket.domain.payment.util;

import store.nightmarket.domain.payment.model.DetailPaymentRecord;
import store.nightmarket.domain.payment.model.PaymentRecord;
import store.nightmarket.domain.payment.model.Product;
import store.nightmarket.domain.payment.state.DetailPaymentState;
import store.nightmarket.domain.payment.valueobject.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PaymentTestUtil {

    public static PaymentRecord createTestPayment(
            UUID recordId,
            UUID userId,
            List<DetailPaymentRecord> details
    ) {
       return PaymentRecord.newInstance(
                new PaymentRecordId(recordId),
                new UserId(userId),
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
            int price
    ) {
        return Product.newInstance(
                new ProductId(productId),
                new Price(BigDecimal.valueOf(price))
        );
    }

}
