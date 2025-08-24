package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ProductVariantDto;

public interface ReadProductVariantPort {

	List<ProductVariantDto> readFetchVariantOptionValue(UUID id);

}
