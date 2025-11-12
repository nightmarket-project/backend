package store.nightmarket.application.apporder.out;

import store.nightmarket.domain.order.model.ProductVariant;

public interface SaveProductVariantPort {

	void save(ProductVariant productVariant);

}
