package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.itemweb.model.id.ProductPostId;

@Service
@RequiredArgsConstructor
public class ReadProductPostUseCase implements BaseUseCase<ProductPostId, Output> {

	private final ReadProductPostPort readProductPostPort;

	@Override
	public Output execute(ProductPostId productPostId) {
		ProductPostAdapterDto productPostAdapterDto = readProductPostPort
			.readOrThrowFetch(productPostId);

		return Output.builder()
			.productPostAdapterDto(productPostAdapterDto)
			.build();
	}

}
