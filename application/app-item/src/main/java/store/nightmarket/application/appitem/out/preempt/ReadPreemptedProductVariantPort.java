package store.nightmarket.application.appitem.out.preempt;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import store.nightmarket.domain.item.exception.PreemptionException;
import store.nightmarket.domain.item.model.PreemptedProductVariant;
import store.nightmarket.domain.item.model.id.PreemptedProductVariantId;

public interface ReadPreemptedProductVariantPort {

	Optional<PreemptedProductVariant> read(PreemptedProductVariantId id);

	default PreemptedProductVariant readOrThrow(PreemptedProductVariantId id) {
		return read(id)
			.orElseThrow(() -> new PreemptionException("Not Found Preemption"));
	}

	Long readPreemptedQuantity(UUID productVariantId, LocalDateTime now);

}
