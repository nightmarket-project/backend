package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadOptionGroupUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.valueobject.ProductId;

@Service
@RequiredArgsConstructor
public class ReadOptionGroupUseCase implements BaseUseCase<ProductId, Output> {

	private final ReadOptionGroupPort readOptionGroupPort;

	@Override
	public Output execute(ProductId productId) {
		List<OptionGroupAdapterDto> optionGroupAdapterDtoList = readOptionGroupPort
			.readFetchOptionValue(productId);

		return Output.builder()
			.optionGroupAdapterDtoList(optionGroupAdapterDtoList)
			.build();
	}

}
