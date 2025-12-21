package store.nightmarket.application.appitem.out;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import store.nightmarket.domain.item.exception.PreemptionException;
import store.nightmarket.domain.item.model.Preemption;
import store.nightmarket.domain.item.model.id.PreemptionId;

public interface ReadPreemptionPort {

	Optional<Preemption> read(PreemptionId id);

	default Preemption readOrThrow(PreemptionId id) {
		return read(id)
			.orElseThrow(() -> new PreemptionException("Not Found Preemption"));
	}

	Long readPreemptedQuantity(UUID productVariantId, LocalDateTime now);

}
