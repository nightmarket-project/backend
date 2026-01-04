package store.nightmarket.application.appitem.out.variant;

import store.nightmarket.domain.item.model.ProductVariant;

public interface SaveProductVariantPort {

	void save(ProductVariant productVariant);

}
