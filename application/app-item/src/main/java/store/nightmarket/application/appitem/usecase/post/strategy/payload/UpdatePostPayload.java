package store.nightmarket.application.appitem.usecase.post.strategy.payload;

import store.nightmarket.domain.itemweb.valueobject.Rating;

public record UpdatePostPayload(
	Rating rating
) implements ScheduleActionPayload {
}