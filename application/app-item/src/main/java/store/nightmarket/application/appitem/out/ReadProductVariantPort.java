package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;

public interface ReadProductVariantPort {

	List<ProductVariantAdapterDto> readFetchVariantOptionValue(UUID id);

}
