package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.UUID;

import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;

public interface ReadOptionGroupPort {

	List<OptionGroupAdapterDto> readFetchOptionValue(UUID id);

}
