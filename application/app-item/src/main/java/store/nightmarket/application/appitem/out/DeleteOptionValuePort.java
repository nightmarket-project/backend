package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;

public interface DeleteOptionValuePort {

	void delete(OptionValueId optionValueId);

	void deleteByOptionGroupId(OptionGroupId optionGroupId);

	void deleteAllByOptionGroupId(List<OptionGroupId> optionGroupIdList);

}
