package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.Map;

import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.valueobject.ProductId;
import store.nightmarket.domain.item.valueobject.ProductVariantId;
import store.nightmarket.itemweb.valueobject.ProductPostId;

public interface ReadProductVariantPort {

	List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId id);

	Map<ProductVariantId, ProductPostId> findProductPostIdsByVariantIds(List<ProductVariantId> productVariantIdList);

}
