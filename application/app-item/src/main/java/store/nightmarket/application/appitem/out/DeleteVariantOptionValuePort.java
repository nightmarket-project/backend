package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.VariantOptionValueId;

public interface DeleteVariantOptionValuePort {

	void delete(VariantOptionValueId variantOptionValueId);

	void deleteByOptionGroupId(OptionGroupId optionGroupId);

	void deleteByOptionValueId(OptionValueId optionValueId);

	void deleteAllByProductVariantIdList(List<ProductVariantId> productVariantId);

}
