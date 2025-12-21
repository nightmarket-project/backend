package store.nightmarket.application.apporder.out.feign;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PreemptRequest(
	UUID orderId,
	List<PreemptProduct> preemptProductList
) {
	@Builder
	public record PreemptProduct(
		UUID productVariantId,
		long quantity
	) {

	}
}
