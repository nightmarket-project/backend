package store.nightmarket.application.appitem.out.adaptor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionValuePort;
import store.nightmarket.application.appitem.out.mapper.OptionValueMapper;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.persistence.persistitem.repository.OptionValueRepository;

@Component
@RequiredArgsConstructor
public class ReadOptionValueJpaAdapter implements ReadOptionValuePort {

	private final OptionValueRepository optionValueRepository;

	@Override
	public Optional<OptionValue> read(OptionValueId optionValueId) {
		return optionValueRepository.findById(optionValueId.getId())
			.map(OptionValueMapper::toDomain);
	}

}
