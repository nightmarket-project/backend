package store.nightmarket.application.apporder.out.feign;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class PreemptApiDto {

	@Builder
	public record PreemptRequest(
		UUID orderId,
		List<PreemptRequestedProduct> preemptRequestedProductList
	) {
		@Builder
		public record PreemptRequestedProduct(
			UUID productVariantId,
			long quantity
		) {

		}
	}

	@Builder
	public record PreemptResponse(
		boolean isSuccess,
		List<UUID> insufficientProductList
	) {

	}
}
