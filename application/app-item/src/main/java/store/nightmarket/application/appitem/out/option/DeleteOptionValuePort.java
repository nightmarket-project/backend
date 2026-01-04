package store.nightmarket.application.appitem.out.option;

import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;

public interface DeleteOptionValuePort {

	void delete(OptionValueId optionValueId);

	void deleteByOptionGroupId(OptionGroupId optionGroupId);

}
