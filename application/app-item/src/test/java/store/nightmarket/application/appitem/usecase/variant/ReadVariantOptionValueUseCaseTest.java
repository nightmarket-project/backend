package store.nightmarket.application.appitem.usecase.variant;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadVariantOptionValuePort;
import store.nightmarket.application.appitem.usecase.variant.dto.ReadVariantOptionValueUseCaseDto;
import store.nightmarket.domain.item.model.VariantOptionValue;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;

public class ReadVariantOptionValueUseCaseTest {

	private SoftAssertions soft;
	private ReadVariantOptionValueUseCase readVariantOptionValueUseCase;
	private ReadVariantOptionValuePort mockReadVariantOptionValuePort;

	@BeforeEach
	void setUp() {
		soft = new SoftAssertions();
		mockReadVariantOptionValuePort = mock(ReadVariantOptionValuePort.class);
		readVariantOptionValueUseCase = new ReadVariantOptionValueUseCase(
			mockReadVariantOptionValuePort
		);
	}

	@Test
	@DisplayName("재고 ID를 통해 VariantOptionValue 값들을 읽는다")
	void readVariantOptionValueByProductVariantId() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());

		VariantOptionValue variantOptionValue = TestDomainFactory.createVariantOptionValue(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID()
		);

		ReadVariantOptionValueUseCaseDto.Input input = ReadVariantOptionValueUseCaseDto.Input.builder()
			.productVariantId(productVariantId)
			.build();

		when(mockReadVariantOptionValuePort.readByIdProductVariantId(productVariantId))
			.thenReturn(List.of(variantOptionValue));

		// when
		ReadVariantOptionValueUseCaseDto.Output output = readVariantOptionValueUseCase.execute(input);

		// then
		verify(mockReadVariantOptionValuePort, times(1))
			.readByIdProductVariantId(productVariantId);

		soft.assertThat(output).isNotNull();
		soft.assertThat(output.variantOptionValueList()).hasSize(1);
		soft.assertThat(output.variantOptionValueList().getFirst()).isEqualTo(variantOptionValue);
		soft.assertAll();
	}

}
