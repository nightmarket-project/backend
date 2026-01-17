package store.nightmarket.application.appitem.out;

import java.util.List;
import java.util.Optional;

import store.nightmarket.application.appitem.out.mapper.dto.OptionGroupAdapterDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;

public interface ReadOptionGroupPort {

	Optional<OptionGroup> read(OptionGroupId optionGroupId);

	default OptionGroup readOrThrow(OptionGroupId optionGroupId) {
		return read(optionGroupId)
			.orElseThrow(() -> new OptionException("Not Found OptionGroup"));
	}

	List<OptionGroupAdapterDto> readFetchOptionValue(ProductId id);

}
