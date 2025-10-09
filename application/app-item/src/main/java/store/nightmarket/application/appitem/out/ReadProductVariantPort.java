package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public interface ReadProductVariantPort {

	List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId id);

	Map<ProductVariantId, ProductPostId> findProductPostIdsByVariantIds(List<ProductVariantId> productVariantIdList);

	Optional<ProductVariant> read(ProductVariantId productVariantId);

	default ProductVariant readOrThrow(ProductVariantId productVariantId) {
		return read(productVariantId)
			.orElseThrow(() -> new ProductException("Product variant not found: " + productVariantId));
	}

}
