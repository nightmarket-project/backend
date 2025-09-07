package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class ReadProductPostUseCase implements BaseUseCase<UUID, Output> {

	private final ReadProductPostPort readProductPostPort;

	@Override
	public Output execute(UUID productPostId) {
		ProductPostAdapterDto productPostAdapterDto = readProductPostPort
			.readOrThrowFetch(productPostId);

		return Output.builder()
			.productPostAdapterDto(productPostAdapterDto)
			.build();
	}

}
