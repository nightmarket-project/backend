package store.nightmarket.domain.order.service.dto;

import lombok.Builder;
import lombok.Getter;
import store.nightmarket.domain.order.model.OrderRecord;

public class RequestOrderDomainServiceDto {

    @Getter
    @Builder
    public static class Input {

        private final OrderRecord orderRecord;

    }

    @Getter
    @Builder
    public static class Event{

        private final OrderRecord orderRecord;

    }

}
