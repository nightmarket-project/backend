package store.nightmarket.application.appitem.out.option;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;

public interface SaveOptionValuePort {

	void save(OptionValue optionValue, OptionGroup optionGroup);

}
