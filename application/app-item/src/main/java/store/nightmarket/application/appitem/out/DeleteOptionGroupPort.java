package store.nightmarket.application.appitem.out;

import store.nightmarket.domain.item.model.id.OptionGroupId;

public interface DeleteOptionGroupPort {

	void deleteById(OptionGroupId optionGroupId);

}
