package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;

@Component
@RequiredArgsConstructor
public class ReadOptionGroupAdaptor implements ReadOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;

	@Override
	public List<OptionGroupAdapterDto> readFetchOptionValue(UUID id) {
		return optionGroupRepository.findByProductPostId(id).stream()
			.map(OptionGroupAdapterDto::toDomain)
			.toList();
	}

}
