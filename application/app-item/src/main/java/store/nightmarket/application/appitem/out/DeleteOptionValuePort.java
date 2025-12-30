package store.nightmarket.application.appitem.out;

import store.nightmarket.domain.item.model.id.OptionValueId;

public interface DeleteOptionValuePort {

	void delete(OptionValueId optionValueId);

}
