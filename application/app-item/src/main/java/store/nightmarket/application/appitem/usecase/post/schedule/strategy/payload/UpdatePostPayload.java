package store.nightmarket.application.appitem.usecase.post.schedule.strategy.payload;

import store.nightmarket.domain.itemweb.valueobject.Rating;

public record UpdatePostPayload(
	Rating rating
) {

}