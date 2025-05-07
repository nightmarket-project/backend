package store.nightmarket.domain.order.status;

public enum DetailOrderState {

	NONE, //
	SUBMITTED, //(주문 제출)
	COMPLETED, //(주문 완료)
	SHIPPED, //(배송 시작)	상품이 발송됨
	DELIVERED, //(배송 완료)	고객에게 상품이 도착함
	CANCELED, //(주문 취소됨)	고객 또는 시스템에 의해 주문이 취소됨
	RETURNED, //(반품됨)	고객이 상품을 반품함
	REFUNDED; //(환불 완료)	결제금액이 환불 처리됨

	public boolean isAbleToChangeToSubmitted() {
		return DetailOrderState.NONE.equals(this);
	}

	public boolean isAbleToChangeToCanceled() {
		return DetailOrderState.COMPLETED.equals(this);
	}

}
