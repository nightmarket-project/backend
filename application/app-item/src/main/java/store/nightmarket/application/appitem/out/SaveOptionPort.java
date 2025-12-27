package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;

public interface SaveOptionPort {

	void save(OptionGroup optionGroup, List<OptionValue> optionValueList);

}
