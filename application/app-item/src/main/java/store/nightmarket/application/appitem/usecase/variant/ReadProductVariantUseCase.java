package store.nightmarket.application.appitem.usecase.variant;

import static store.nightmarket.application.appitem.usecase.variant.dto.ReadProductVariantUseCaseDto.*;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.variant.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.variant.mapper.dto.ProductVariantAdapterDto;
import store.nightmarket.common.application.usecase.BaseUseCase;
import store.nightmarket.domain.item.model.id.ProductId;

@Service
@RequiredArgsConstructor
public class ReadProductVariantUseCase implements BaseUseCase<ProductId, Output> {

	private final ReadProductVariantPort readProductVariantPort;

	@Override
	public Output execute(ProductId productId) {
		List<ProductVariantAdapterDto> productVariantAdapterDtoList = readProductVariantPort
			.readFetchVariantOptionValue(productId);

		return Output.builder()
			.productVariantAdapterDtoList(productVariantAdapterDtoList)
			.build();
	}

}
