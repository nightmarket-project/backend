package store.nightmarket.application.appitem.usecase.option;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.option.SaveOptionValuePort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionValueUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterOptionValueUseCaseTest {

	private RegisterOptionValueUseCase registerOptionValueUseCase;
	private ReadProductPort mockReadProductPort;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private SaveOptionValuePort mockSaveOptionValuePort;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockSaveOptionValuePort = mock(SaveOptionValuePort.class);
		registerOptionValueUseCase = new RegisterOptionValueUseCase(
			mockReadProductPort,
			mockReadOptionGroupPort,
			mockSaveOptionValuePort
		);
	}

	@Test
	@DisplayName("옵션밸류를 등록한다")
	void registerOptionValue() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		RegisterOptionValueUseCaseDto.Input input = RegisterOptionValueUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.userId(userId)
			.name(new Name("옵션 밸류"))
			.price(new Price(BigInteger.valueOf(1000)))
			.displayOrder(1)
			.build();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		// when
		registerOptionValueUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockSaveOptionValuePort, times(1))
			.save(any(OptionValue.class), any(OptionGroup.class));
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 등록을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		RegisterOptionValueUseCaseDto.Input input = RegisterOptionValueUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.userId(userId)
			.name(new Name("옵션 밸류"))
			.price(new Price(BigInteger.valueOf(1000)))
			.displayOrder(1)
			.build();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		// when
		// then
		assertThatThrownBy(() -> registerOptionValueUseCase.execute(input))
			.isInstanceOf(OptionException.class);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadOptionGroupPort, never())
			.readOrThrow(optionGroupId);

		verify(mockSaveOptionValuePort, never())
			.save(any(OptionValue.class), any(OptionGroup.class));
	}

}
