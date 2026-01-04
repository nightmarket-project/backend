package store.nightmarket.application.appitem.out.option;

import store.nightmarket.domain.item.model.id.OptionGroupId;

public interface DeleteOptionGroupPort {

	void deleteById(OptionGroupId optionGroupId);

}
