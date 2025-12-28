package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;
import store.nightmarket.application.appitem.out.mapper.OptionGroupMapper;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;

@Component
@RequiredArgsConstructor
public class ReadOptionGroupAdapter implements ReadOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;

	@Override
	public Optional<OptionGroup> read(OptionGroupId optionGroupId) {
		return optionGroupRepository.findByIdFetchOptionValue(optionGroupId.getId())
			.map(OptionGroupMapper::toDomain);
	}

	@Override
	public List<OptionGroupAdapterDto> readFetchOptionValue(ProductId productId) {
		return optionGroupRepository.findByProductPostId(productId.getId()).stream()
			.map(OptionGroupAdapterDto::toDomain)
			.toList();
	}

}
