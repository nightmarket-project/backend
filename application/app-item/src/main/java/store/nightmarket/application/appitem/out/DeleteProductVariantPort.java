package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.id.ProductVariantId;

public interface DeleteProductVariantPort {

	void delete(ProductVariantId productVariantId);

	void deleteAll(List<ProductVariantId> productVariantIdList);

}
