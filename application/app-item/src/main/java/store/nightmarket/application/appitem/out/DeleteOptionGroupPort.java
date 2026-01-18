package store.nightmarket.application.appitem.out;

import java.util.List;

import store.nightmarket.domain.item.model.id.OptionGroupId;

public interface DeleteOptionGroupPort {

	void deleteById(OptionGroupId optionGroupId);

	void deleteAll(List<OptionGroupId> optionGroupIdList);

}
