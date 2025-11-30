package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.Map;

import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.itemweb.model.id.ProductPostId;

public interface ReadProductVariantPort {

	List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId id);

	Map<ProductVariantId, ProductPostId> findProductPostIdsByVariantIds(List<ProductVariantId> productVariantIdList);

	List<ProductVariant> readByIdList(List<ProductVariantId> productVariantId);

}
