package store.nightmarket.application.appitem.out.adaptor;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.persistence.persistitem.repository.OptionGroupRepository;

@Component
@RequiredArgsConstructor
public class ReadOptionGroupAdaptor implements ReadOptionGroupPort {

	private final OptionGroupRepository optionGroupRepository;

	@Override
	public List<OptionGroupAdapterDto> readFetchOptionValue(ProductId productId) {
		return optionGroupRepository.findByProductPostId(productId.getId()).stream()
			.map(OptionGroupAdapterDto::toDomain)
			.toList();
	}

}
