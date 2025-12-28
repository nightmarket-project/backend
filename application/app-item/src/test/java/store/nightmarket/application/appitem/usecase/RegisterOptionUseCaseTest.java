package store.nightmarket.application.appitem.usecase;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.out.SaveOptionPort;
import store.nightmarket.application.appitem.usecase.dto.RegisterOptionUseCaseDto;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterOptionUseCaseTest {

	private RegisterOptionUseCase registerOptionUseCase;
	private SaveOptionPort mockSaveOptionPort;

	@BeforeEach
	void setUp() {
		mockSaveOptionPort = mock(SaveOptionPort.class);
		registerOptionUseCase = new RegisterOptionUseCase(mockSaveOptionPort);
	}

	@Test
	@DisplayName("옵션 그룹과 그룹에 속한 값들을 등록한다")
	void registerOptionGroupAndValue() {
		// given
		RegisterOptionUseCaseDto.Input input = RegisterOptionUseCaseDto.Input.builder()
			.productId(new ProductId(UUID.randomUUID()))
			.userId(new UserId(UUID.randomUUID()))
			.name(new Name("옵션 그룹"))
			.displayOrder(1)
			.optionValueDtoList(
				List.of(
					RegisterOptionUseCaseDto.OptionValueDto.builder()
						.name(new Name("옵션 값1"))
						.price(new Price(BigInteger.valueOf(50000)))
						.displayOrder(1)
						.build(),
					RegisterOptionUseCaseDto.OptionValueDto.builder()
						.name(new Name("옵션 값2"))
						.price(new Price(BigInteger.valueOf(100000)))
						.displayOrder(2)
						.build()
				)
			)
			.build();
		// when
		registerOptionUseCase.execute(input);

		// then
		verify(mockSaveOptionPort, times(1))
			.save(any(OptionGroup.class), anyList());
	}
}
