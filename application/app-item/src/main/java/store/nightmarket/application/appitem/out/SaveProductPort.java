package store.nightmarket.application.appitem.out;

import store.nightmarket.domain.item.model.Product;

public interface SaveProductPort {

	void save(Product product);

}
