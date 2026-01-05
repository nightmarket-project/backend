package store.nightmarket.application.appitem.out.option.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.option.mapper.dto.OptionGroupAdapterDto;
import store.nightmarket.application.appitem.out.option.mapper.OptionGroupMapper;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
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
		return optionGroupRepository.findById(optionGroupId.getId())
			.map(OptionGroupMapper::toDomain);
	}

	@Override
	public List<OptionGroupAdapterDto> readFetchOptionValue(ProductId productId) {
		return optionGroupRepository.findByProductId(productId.getId()).stream()
			.map(OptionGroupAdapterDto::toDomain)
			.toList();
	}

}
