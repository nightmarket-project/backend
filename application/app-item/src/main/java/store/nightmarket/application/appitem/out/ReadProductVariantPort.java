package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.domain.item.valueobject.ProductId;

public interface ReadProductVariantPort {

	List<ProductVariantAdapterDto> readFetchVariantOptionValue(ProductId id);

}
