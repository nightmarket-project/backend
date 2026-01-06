package store.nightmarket.application.appitem.usecase.option;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.option.SaveOptionGroupPort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.application.appitem.usecase.option.dto.RegisterOptionGroupUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterOptionGroupUseCaseTest {

	private RegisterOptionGroupUseCase registerOptionGroupUseCase;
	private ReadProductPort mockReadProductPort;
	private SaveOptionGroupPort mockSaveOptionGroupPort;

	@BeforeEach
	void setUp() {
		mockSaveOptionGroupPort = mock(SaveOptionGroupPort.class);
		mockReadProductPort = mock(ReadProductPort.class);
		registerOptionGroupUseCase = new RegisterOptionGroupUseCase(
			mockReadProductPort,
			mockSaveOptionGroupPort
		);
	}

	@Test
	@DisplayName("옵션 그룹과 그룹에 속한 값들을 등록한다")
	void registerOptionGroupAndValue() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		RegisterOptionGroupUseCaseDto.Input input = RegisterOptionGroupUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.name(new Name("옵션 그룹"))
			.displayOrder(1)
			.optionValueDtoList(
				List.of(
					RegisterOptionGroupUseCaseDto.OptionValueDto.builder()
						.name(new Name("옵션 값1"))
						.price(new Price(BigInteger.valueOf(50000)))
						.displayOrder(1)
						.build(),
					RegisterOptionGroupUseCaseDto.OptionValueDto.builder()
						.name(new Name("옵션 값2"))
						.price(new Price(BigInteger.valueOf(100000)))
						.displayOrder(2)
						.build()
				)
			)
			.build();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		registerOptionGroupUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockSaveOptionGroupPort, times(1))
			.save(any(OptionGroup.class), anyList());
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 등록을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		RegisterOptionGroupUseCaseDto.Input input = RegisterOptionGroupUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.name(new Name("옵션 그룹"))
			.displayOrder(1)
			.optionValueDtoList(
				List.of(
					RegisterOptionGroupUseCaseDto.OptionValueDto.builder()
						.name(new Name("옵션 값1"))
						.price(new Price(BigInteger.valueOf(50000)))
						.displayOrder(1)
						.build(),
					RegisterOptionGroupUseCaseDto.OptionValueDto.builder()
						.name(new Name("옵션 값2"))
						.price(new Price(BigInteger.valueOf(100000)))
						.displayOrder(2)
						.build()
				)
			)
			.build();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> registerOptionGroupUseCase.execute(input))
			.isInstanceOf(OptionException.class);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockSaveOptionGroupPort, never())
			.save(any(OptionGroup.class), anyList());
	}

}
