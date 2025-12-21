package store.nightmarket.application.appitem.in.dto;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

public class PreemptProductDto {

	@Builder
	public record Request(
		UUID orderId,
		List<PreemptProduct> preemptProductList
	) {

	}

	@Builder
	public record PreemptProduct(
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
