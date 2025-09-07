package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadOptionGroupUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class ReadOptionGroupUseCase implements BaseUseCase<UUID, Output> {

	private final ReadOptionGroupPort readOptionGroupPort;

	@Override
	public Output execute(UUID productId) {
		List<OptionGroupAdapterDto> optionGroupAdapterDtoList = readOptionGroupPort.readFetchOptionValue(productId);

		return Output.builder()
			.optionGroupAdapterDtoList(optionGroupAdapterDtoList)
			.build();
	}

}
