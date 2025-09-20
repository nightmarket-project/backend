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
import store.nightmarket.application.appitem.out.dto.ProductVariantAdapterDto;
import store.nightmarket.application.appitem.out.dto.VariantOptionValueAdapterDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductVariantUseCaseDto;
import store.nightmarket.domain.item.valueobject.ProductId;

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
		ProductId productId = new ProductId(UUID.randomUUID());
		UUID productVariantId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		UUID variantOptionValueId = UUID.randomUUID();
		UUID optionGroupId = UUID.randomUUID();
		UUID optionValueId = UUID.randomUUID();

		ProductVariantAdapterDto productVariantAdapterDto = ProductVariantAdapterDto.builder()
			.productVariant(TestDomainFactory.createProductVariant(productVariantId, productId.getId(), userId))
			.variantOptionValueAdapterDtoList(
				List.of(
					VariantOptionValueAdapterDto.builder()
						.variantOptionValue(TestDomainFactory.createVariantOptionValue(
							variantOptionValueId, productVariantId, optionGroupId, optionValueId))
						.build()))
			.build();

		when(mockReadProductVariantPort.readFetchVariantOptionValue(productId))
			.thenReturn(List.of(productVariantAdapterDto));

		// when
		ReadProductVariantUseCaseDto.Output output = readProductVariantUseCase.execute(productId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.productVariantAdapterDtoList())
			.hasSize(1);
		assertThat(output.productVariantAdapterDtoList().getFirst())
			.isEqualTo(productVariantAdapterDto);
		verify(mockReadProductVariantPort, times(1))
			.readFetchVariantOptionValue(productId);
	}

}
