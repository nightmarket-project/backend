package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.OptionGroupDto;
import store.nightmarket.domain.item.model.OptionGroup;

public interface ReadOptionGroupPort {

	List<OptionGroup> read(UUID id);

	List<OptionGroupDto> readFetchOptionValue(UUID id);

}
