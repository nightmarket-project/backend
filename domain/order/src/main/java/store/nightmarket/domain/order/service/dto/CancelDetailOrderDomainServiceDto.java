package store.nightmarket.domain.order.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.order.model.OrderRecord;
import store.nightmarket.domain.order.valueobject.DetailOrderRecordId;

public class CancelDetailOrderDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final OrderRecord orderRecord;
        private final DetailOrderRecordId detailOrderRecordId;

    }

    @Getter
    @Builder
    public static class Event{

        private final OrderRecord orderRecord;
        private final DetailOrderRecordId detailOrderRecordId;

    }

}
