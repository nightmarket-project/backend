package store.nightmarket.application.appitem.usecase.option;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadOptionValuePort;
import store.nightmarket.application.appitem.out.SaveOptionValuePort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.usecase.option.dto.ModifyOptionValueUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.service.ModifyOptionValueDomainService;
import store.nightmarket.domain.item.service.dto.ModifyOptionValueDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyOptionValueUseCaseTest {

	private ModifyOptionValueUseCase modifyOptionValueUseCase;
	private ReadProductPort mockReadProductPort;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private ReadOptionValuePort mockReadOptionValuePort;
	private SaveOptionValuePort mockSaveOptionValuePort;
	private ModifyOptionValueDomainService mockModifyOptionValueDomainService;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockReadOptionValuePort = mock(ReadOptionValuePort.class);
		mockSaveOptionValuePort = mock(SaveOptionValuePort.class);
		mockModifyOptionValueDomainService = mock(ModifyOptionValueDomainService.class);
		modifyOptionValueUseCase = new ModifyOptionValueUseCase(
			mockReadProductPort,
			mockReadOptionGroupPort,
			mockReadOptionValuePort,
			mockSaveOptionValuePort,
			mockModifyOptionValueDomainService
		);
	}

	@Test
	@DisplayName("옵션 밸류의 내용을 수정한다")
	void modifyOptionValue() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		OptionValueId optionValueId = new OptionValueId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		OptionValue optionValue = TestDomainFactory.createOptionValue(
			optionValueId.getId(),
			optionGroupId.getId()
		);

		Name modifiedName = new Name("modified name");
		Price modifiedPrice = new Price(BigInteger.valueOf(2000));
		int modifiedOrder = 2;

		OptionValue modifiedOptionValue = OptionValue.newInstance(
			optionValueId,
			optionGroupId,
			modifiedName,
			modifiedPrice,
			modifiedOrder
		);

		ModifyOptionValueUseCaseDto.Input input = ModifyOptionValueUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.optionValueId(optionValueId)
			.userId(userId)
			.name(modifiedName)
			.price(modifiedPrice)
			.displayOrder(modifiedOrder)
			.build();

		ModifyOptionValueDomainServiceDto.Input domainInput = ModifyOptionValueDomainServiceDto.Input.builder()
			.optionValue(optionValue)
			.name(input.name())
			.price(input.price())
			.order(input.displayOrder())
			.build();

		ModifyOptionValueDomainServiceDto.Event event = ModifyOptionValueDomainServiceDto.Event.builder()
			.optionValue(modifiedOptionValue)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		when(mockReadOptionValuePort.readOrThrow(optionValueId))
			.thenReturn(optionValue);

		when(mockModifyOptionValueDomainService.execute(domainInput))
			.thenReturn(event);

		// when
		modifyOptionValueUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockReadOptionValuePort, times(1))
			.readOrThrow(optionValueId);

		verify(mockModifyOptionValueDomainService, times(1))
			.execute(domainInput);

		ArgumentCaptor<OptionValue> argumentCaptorValue = ArgumentCaptor.forClass(OptionValue.class);
		ArgumentCaptor<OptionGroup> argumentCaptorGroup = ArgumentCaptor.forClass(OptionGroup.class);

		verify(mockSaveOptionValuePort, times(1))
			.save(argumentCaptorValue.capture(), argumentCaptorGroup.capture());

		OptionValue saveOptionValue = argumentCaptorValue.getValue();

		Assertions.assertThat(modifiedOptionValue.getOptionGroupId()).isEqualTo(saveOptionValue.getOptionGroupId());
		Assertions.assertThat(modifiedOptionValue.getName()).isEqualTo(saveOptionValue.getName());
		Assertions.assertThat(modifiedOptionValue.getPrice()).isEqualTo(saveOptionValue.getPrice());
		Assertions.assertThat(modifiedOptionValue.getOrder()).isEqualTo(saveOptionValue.getOrder());
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 수정을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		OptionValueId optionValueId = new OptionValueId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		Name modifiedName = new Name("modified name");
		Price modifiedPrice = new Price(BigInteger.valueOf(2000));
		int modifiedOrder = 2;

		ModifyOptionValueUseCaseDto.Input input = ModifyOptionValueUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.optionValueId(optionValueId)
			.userId(userId)
			.name(modifiedName)
			.price(modifiedPrice)
			.displayOrder(modifiedOrder)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		assertThatThrownBy(() -> modifyOptionValueUseCase.execute(input))
			.isInstanceOf(OptionException.class);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadOptionGroupPort, never())
			.readOrThrow(optionGroupId);

		verify(mockReadOptionValuePort, never())
			.readOrThrow(optionValueId);

		verify(mockModifyOptionValueDomainService, never())
			.execute(any());

		verify(mockSaveOptionValuePort, never())
			.save(any(), any());
	}

}
