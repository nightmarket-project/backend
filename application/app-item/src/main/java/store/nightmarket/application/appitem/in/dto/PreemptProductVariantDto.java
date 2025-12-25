package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class PreemptProductVariantDto {

	@Builder
	public record Request(
		UUID orderId,
		List<PreemptRequestedProduct> preemptRequestedProductList
	) {

	}

	@Builder
	public record PreemptRequestedProduct(
		UUID productVariantId,
		long quantity
	) {

	}

	@Builder
	public record Response(
		boolean isSuccess,
		List<UUID> insufficientProductList
	) {

	}

}
