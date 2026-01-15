package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.VariantOptionValue;

public interface SaveVariantOptionValuePort {

	void saveAll(List<VariantOptionValue> variantOptionValueList);

}
