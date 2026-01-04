package store.nightmarket.application.appitem.out.variant;

import java.util.List;
import java.util.Optional;

import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;

public interface ReadVariantOptionValuePort {

	Optional<VariantOptionValue> read(VariantOptionValueId variantOptionValueId);

	default VariantOptionValue readOrThrow(VariantOptionValueId variantOptionValueId) {
		return read(variantOptionValueId)
			.orElseThrow(() -> new OptionException("Not Found VariantOptionValue"));
	}

	List<ProductVariantId> readProductVariantIdsByOptionGroupId(OptionGroupId optionGroupId);

	List<ProductVariantId> readProductVariantIdsByOptionValueId(OptionValueId optionValueId);

}
