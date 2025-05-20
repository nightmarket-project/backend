package store.nightmarket.domain.order.util;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.service.dto.CancelDetailOrderDomainServiceDto;
import store.nightmarket.domain.order.service.dto.CancelOrderDomainServiceDto;
import store.nightmarket.domain.order.service.dto.RequestOrderDomainServiceDto;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductId;
import store.nightmarket.domain.order.valueobject.Quantity;
import store.nightmarket.domain.order.valueobject.UserId;

public class OrderTestUtil {

	public static DetailOrderRecord makeDetailOrderRecord(
		UUID recordId,
		UUID productId,
		int value,
		DetailOrderState state
	) {
		return DetailOrderRecord.newInstance(
			new DetailOrderRecordId(recordId),
			new ProductId(productId),
			new Quantity(value),
			state
		);
	}

	public static OrderRecord makeOrderRecord(List<DetailOrderRecord> list) {
		return OrderRecord.newInstance(
			new OrderRecordId(UUID.randomUUID()),
			new Address("111111", "하늘로 111-111", "구름아파트 111동 111호"),
			LocalDate.now(),
			new UserId(UUID.randomUUID()),
			list
		);
	}

	public static RequestOrderDomainServiceDto.Input makeRequestInput(OrderRecord orderRecord) {
		return RequestOrderDomainServiceDto.Input.builder()
			.orderRecord(orderRecord)
			.build();
	}

	public static CancelOrderDomainServiceDto.Input makeCancelinput(OrderRecord orderRecord) {
		return CancelOrderDomainServiceDto.Input.builder()
			.orderRecord(orderRecord)
			.build();
	}

	public static CancelDetailOrderDomainServiceDto.Input makeDetailCancelInput(OrderRecord orderRecord,
		DetailOrderRecord detailOrderRecord) {
		return CancelDetailOrderDomainServiceDto.Input.builder()
			.orderRecord(orderRecord)
			.detailOrderRecord(detailOrderRecord)
			.build();
	}

}
