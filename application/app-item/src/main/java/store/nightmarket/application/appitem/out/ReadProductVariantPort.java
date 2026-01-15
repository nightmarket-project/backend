package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import store.nightmarket.application.appitem.out.mapper.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public interface ReadProductVariantPort {

	Optional<ProductVariant> read(ProductVariantId id);

	default ProductVariant readOrThrow(ProductVariantId id) {
		return read(id)
			.orElseThrow(() -> new ProductException("Not Found ProductVariant"));
	}

	List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId id);

	Map<ProductVariantId, ProductPostId> findProductPostIdsByVariantIds(List<ProductVariantId> productVariantIdList);

	List<ProductVariant> readByIdList(List<ProductVariantId> productVariantId);

}
