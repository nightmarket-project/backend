package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.ProductVariantDto;
import store.nightmarket.domain.item.model.ProductVariant;

public interface ReadProductVariantPort {

	List<ProductVariant> read(UUID id);

	List<ProductVariantDto> readFetchVariantOptionValue(UUID id);

}
