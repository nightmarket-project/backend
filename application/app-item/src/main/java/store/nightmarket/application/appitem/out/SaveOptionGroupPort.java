package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;

public interface SaveOptionGroupPort {

	void save(OptionGroup optionGroup);

	void save(OptionGroup optionGroup, List<OptionValue> optionValueList);

}
