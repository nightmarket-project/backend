package store.nightmarket.persistence.persistitem.repository.dto;

import java.util.UUID;

public record ProductVariantPostIdSummary(
	UUID productPostId,
	UUID productVariantId
) {
}
