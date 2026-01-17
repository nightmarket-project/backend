package store.nightmarket.application.appitem.out;

import java.util.Optional;

import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.id.OptionValueId;

public interface ReadOptionValuePort {

	Optional<OptionValue> read(OptionValueId optionValueId);

	default OptionValue readOrThrow(OptionValueId optionValueId) {
		return read(optionValueId)
			.orElseThrow(() -> new OptionException("Not Found Option Value"));
	}

}
