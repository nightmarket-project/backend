package store.nightmarket.application.apporder.out;

import java.util.List;
import java.util.Optional;

import store.nightmarket.domain.order.exception.OrderException;
import store.nightmarket.domain.order.model.ProductVariant;
import store.nightmarket.domain.order.valueobject.ProductVariantId;

public interface ReadProductVariantPort {

	Optional<ProductVariant> read(ProductVariantId id);

	default ProductVariant readOrThrow(ProductVariantId id) {
		return read(id)
			.orElseThrow(() -> new OrderException("Not Found ProductVariant"));
	}

	List<ProductVariant> readByIdList(List<ProductVariantId> idList);

}
