package store.nightmarket.domain.delivery.state;

public enum DetailDeliveryState {

    NONE,
    PREPARING, // 물품 준비 중
    SHIPPED, // 출고 완료
    IN_DELIVERY, // 배송 중
    DELIVERED, // 배송 완료
    CANCELLED, // 주문 취소
    RETURNED; // 반품 됨
}
