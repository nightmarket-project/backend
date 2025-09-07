package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadProductVariantUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class ReadProductVariantUseCase implements BaseUseCase<UUID, Output> {

	private final ReadProductVariantPort readProductVariantPort;

	@Override
	public Output execute(UUID productId) {
		List<ProductVariantAdapterDto> productVariantAdapterDtoList = readProductVariantPort.readFetchVariantOptionValue(
			productId);

		return Output.builder()
			.productVariantAdapterDtoList(productVariantAdapterDtoList)
			.build();
	}

}
