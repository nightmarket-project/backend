package store.nightmarket.application.apporder.out.feign;

import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PreemptResponse(
	boolean isSuccess,
	List<UUID> insufficientProductList
) {

}
