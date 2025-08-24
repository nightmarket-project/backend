package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.OptionGroupDto;

public interface ReadOptionGroupPort {

	List<OptionGroupDto> readFetchOptionValue(UUID id);

}
