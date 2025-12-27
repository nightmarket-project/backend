package store.nightmarket.application.appitem.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;

public interface ReadProductPort {

	Optional<Product> read(ProductId id);

	default Product readOrThrow(ProductId id) {
		return read(id)
			.orElseThrow(() -> new ProductException("Not Found Product"));
	}

	Page<Product> readAll(Pageable pageable);

}
