package store.nightmarket.application.appitem.usecase;

import static store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.dto.OptionGroupDto;
import store.nightmarket.application.appitem.out.dto.ProductPostDto;
import store.nightmarket.application.appitem.out.dto.ProductVariantDto;
import store.nightmarket.common.application.usecase.BaseUseCase;

@Service
@RequiredArgsConstructor
public class ReadProductPostUseCase implements BaseUseCase<UUID, Output> {

	private final ReadProductPostPort readProductPostPort;
	private final ReadOptionGroupPort readOptionGroupPort;
	private final ReadProductVariantPort readProductVariantPort;

	@Override
	public Output execute(UUID productPostId) {
		ProductPostDto productPostDto = readProductPostPort
			.readOrThrowFetchWithProductAndReviewAndReplies(productPostId);

		UUID productId = productPostDto.getProductPost().getProductId().getId();

		List<OptionGroupDto> optionGroupDtoList = readOptionGroupPort.readFetchOptionValue(productId);
		List<ProductVariantDto> productVariantDtoList = readProductVariantPort.readFetchVariantOptionValue(productId);

		return Output.builder()
			.productPostDto(productPostDto)
			.optionGroupDtoList(optionGroupDtoList)
			.productVariantDtoList(productVariantDtoList)
			.build();
	}

}
