package store.nightmarket.application.apporder.in;

import store.nightmarket.application.apporder.in.dto.ProductCreatedEvent;

public interface ProductCreatedEventListener {

	void handleEvent(ProductCreatedEvent event);

}
