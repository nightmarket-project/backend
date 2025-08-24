package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupDto;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;

@Component
@RequiredArgsConstructor
public class ReadOptionGroupAdaptor implements ReadOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;

	@Override
	public List<OptionGroupDto> readFetchOptionValue(UUID id) {
		return optionGroupRepository.findByProductPostId(id).stream()
			.map(OptionGroupDto::toDomain)
			.toList();
	}

}
