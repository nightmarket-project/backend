package store.nightmarket.application.apporder.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import store.nightmarket.application.apporder.usecase.dto.RequestOrderUseCaseDto;
import store.nightmarket.domain.order.model.DetailOrderRecord;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.status.DetailOrderState;
import store.nightmarket.domain.order.valueobject.Address;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;
import store.nightmarket.domain.order.valueobject.OrderRecordId;
import store.nightmarket.domain.order.valueobject.ProductVariantId;
import store.nightmarket.domain.order.valueobject.Quantity;
import store.nightmarket.domain.order.valueobject.UserId;

public class OrderTestUtil {

	public static RequestOrderUseCaseDto.AddressDto makeAddress() {
		return RequestOrderUseCaseDto.AddressDto.builder()
			.zipCode("111-111")
			.roadAddress("구름로 111-111")
			.detailAddress("111동 111호")
			.build();
	}

	public static OrderRecord makeOrderRecord() {
		return OrderRecord.newInstance(
			new OrderRecordId(UUID.randomUUID()),
			new Address(
				"111-111",
				"구름로 111-111",
				"111동 111호"
			),
			LocalDate.now(),
			new UserId(UUID.randomUUID()),
			new ArrayList<>(List.of(makeDetailOrderRecord()))
		);
	}

	public static DetailOrderRecord makeDetailOrderRecord() {
		return DetailOrderRecord.newInstance(
			new DetailOrderRecordId(UUID.randomUUID()),
			new ProductVariantId(UUID.randomUUID()),
			new Quantity(1),
			DetailOrderState.SUBMITTED
		);
	}

}
