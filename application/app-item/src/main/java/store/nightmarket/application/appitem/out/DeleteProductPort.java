package store.nightmarket.application.appitem.out;

import store.nightmarket.domain.item.model.id.ProductId;

public interface DeleteProductPort {

	void delete(ProductId productId);

}
