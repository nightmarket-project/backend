package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.dto.ProductVariantDto;
import store.nightmarket.application.appitem.out.dto.VariantOptionValueDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductVariantUseCaseDto;

class ReadProductVariantUseCaseTest {

	private ReadProductVariantUseCase readProductVariantUseCase;
	private ReadProductVariantPort mockReadProductVariantPort;

	@BeforeEach
	void setUp() {
		mockReadProductVariantPort = Mockito.mock(ReadProductVariantPort.class);
		readProductVariantUseCase = new ReadProductVariantUseCase(mockReadProductVariantPort);
	}

	@Test
	@DisplayName("제품 아이디를 가지고 ProductVariant를 읽는다.")
	void readProductVariantWithProductId() {
		// given
		UUID productId = UUID.randomUUID();
		UUID productVariantId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		UUID variantOptionValueId = UUID.randomUUID();
		UUID optionGroupId = UUID.randomUUID();
		UUID optionValueId = UUID.randomUUID();

		ProductVariantDto productVariantDto = ProductVariantDto.builder()
			.productVariant(TestDomainFactory.createProductVariant(productVariantId, productId, userId))
			.variantOptionValueDtoList(
				List.of(
					VariantOptionValueDto.builder()
						.variantOptionValue(TestDomainFactory.createVariantOptionValue(
							variantOptionValueId, productVariantId, optionGroupId, optionValueId))
						.build()))
			.build();

		when(mockReadProductVariantPort.readFetchVariantOptionValue(productId))
			.thenReturn(List.of(productVariantDto));

		// when
		ReadProductVariantUseCaseDto.Output output = readProductVariantUseCase.execute(productId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.productVariantDtoList())
			.hasSize(1);
		assertThat(output.productVariantDtoList().getFirst())
			.isEqualTo(productVariantDto);
		verify(mockReadProductVariantPort, times(1))
			.readFetchVariantOptionValue(productId);
	}

}
