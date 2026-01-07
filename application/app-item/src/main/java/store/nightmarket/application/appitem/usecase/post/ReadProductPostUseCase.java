package store.nightmarket.application.appitem.usecase.post;

import static store.nightmarket.application.appitem.usecase.post.dto.ReadProductPostUseCaseDto.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.post.ReadProductPostPort;
import store.nightmarket.application.appitem.out.post.mapper.dto.ProductPostAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

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
