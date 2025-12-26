package store.nightmarket.application.appitem.usecase;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.out.SaveProductVariantPort;
import store.nightmarket.application.appitem.out.SaveVariantOptionValuePort;
import store.nightmarket.application.appitem.usecase.dto.RegisterProductVariantUseCaseDto;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Quantity;

public class RegisterProductVariantUseCaseTest {

	private RegisterProductVariantUseCase registerProductVariantUseCase;
	private SaveProductVariantPort mockSaveProductVariantPort;
	private SaveVariantOptionValuePort mockSaveVariantOptionValuePort;

	@BeforeEach
	void setUp() {
		mockSaveProductVariantPort = mock(SaveProductVariantPort.class);
		mockSaveVariantOptionValuePort = mock(SaveVariantOptionValuePort.class);
		registerProductVariantUseCase = new RegisterProductVariantUseCase(
			mockSaveProductVariantPort,
			mockSaveVariantOptionValuePort
		);
	}

	@Test
	@DisplayName("VariantOptionValue와 ProductVariant가 등록된다")
	void test() {
		// given
		RegisterProductVariantUseCaseDto.Input input = RegisterProductVariantUseCaseDto.Input.builder()
			.productId(new ProductId(UUID.randomUUID()))
			.userId(new UserId(UUID.randomUUID()))
			.SKUCode("재고 코드")
			.quantity(new Quantity(BigInteger.valueOf(100)))
			.optionCombinationList(
				List.of(
					RegisterProductVariantUseCaseDto.OptionCombination.builder()
						.optionGroupId(new OptionGroupId(UUID.randomUUID()))
						.optionValueId(new OptionValueId(UUID.randomUUID()))
						.build(),
					RegisterProductVariantUseCaseDto.OptionCombination.builder()
						.optionGroupId(new OptionGroupId(UUID.randomUUID()))
						.optionValueId(new OptionValueId(UUID.randomUUID()))
						.build()
				)
			)
			.build();

		// when
		registerProductVariantUseCase.execute(input);

		// then
		verify(mockSaveProductVariantPort, times(1))
			.save(any(ProductVariant.class));
		verify(mockSaveVariantOptionValuePort, times(1))
			.saveAll(anyList());
	}

}
