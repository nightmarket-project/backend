package store.nightmarket.application.appitem.usecase.option;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.option.SaveOptionGroupPort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.application.appitem.usecase.option.dto.ModifyOptionGroupUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.service.ModifyOptionGroupDomainService;
import store.nightmarket.domain.item.service.dto.ModifyOptionGroupDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Name;

public class ModifyOptionGroupUseCaseTest {

	private ModifyOptionGroupUseCase modifyOptionGroupUseCase;
	private ReadProductPort mockReadProductPort;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private SaveOptionGroupPort mockSaveOptionGroupPort;
	private ModifyOptionGroupDomainService mockModifyOptionGroupDomainService;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockSaveOptionGroupPort = mock(SaveOptionGroupPort.class);
		mockModifyOptionGroupDomainService = mock(ModifyOptionGroupDomainService.class);
		modifyOptionGroupUseCase = new ModifyOptionGroupUseCase(
			mockReadProductPort,
			mockReadOptionGroupPort,
			mockSaveOptionGroupPort,
			mockModifyOptionGroupDomainService
		);
	}

	@Test
	@DisplayName("옵션 그룹의 내용을 수정한다")
	void modifyOptionGroup() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		Name modifiedName = new Name("modified name");
		int modifiedOrder = 2;

		OptionGroup modifiedOptionGroup = OptionGroup.newInstance(
			optionGroupId,
			productId,
			modifiedName,
			modifiedOrder
		);

		ModifyOptionGroupUseCaseDto.Input input = ModifyOptionGroupUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.userId(userId)
			.name(modifiedName)
			.displayOrder(modifiedOrder)
			.build();

		ModifyOptionGroupDomainServiceDto.Input domainInput = ModifyOptionGroupDomainServiceDto.Input.builder()
			.optionGroup(optionGroup)
			.name(input.name())
			.order(input.displayOrder())
			.build();

		ModifyOptionGroupDomainServiceDto.Event event = ModifyOptionGroupDomainServiceDto.Event.builder()
			.optionGroup(modifiedOptionGroup)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		when(mockModifyOptionGroupDomainService.execute(domainInput))
			.thenReturn(event);

		// when
		modifyOptionGroupUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockModifyOptionGroupDomainService, times(1))
			.execute(domainInput);

		ArgumentCaptor<OptionGroup> argumentCaptor = ArgumentCaptor.forClass(OptionGroup.class);

		verify(mockSaveOptionGroupPort, times(1))
			.save(argumentCaptor.capture());

		OptionGroup saveOptionGroup = argumentCaptor.getValue();

		Assertions.assertThat(modifiedOptionGroup.getOptionGroupId()).isEqualTo(saveOptionGroup.getOptionGroupId());
		Assertions.assertThat(modifiedOptionGroup.getName()).isEqualTo(saveOptionGroup.getName());
		Assertions.assertThat(modifiedOptionGroup.getOrder()).isEqualTo(saveOptionGroup.getOrder());
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 수정을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		Name modifiedName = new Name("modified name");
		int modifiedOrder = 2;
		
		ModifyOptionGroupUseCaseDto.Input input = ModifyOptionGroupUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.userId(userId)
			.name(modifiedName)
			.displayOrder(modifiedOrder)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		assertThatThrownBy(() -> modifyOptionGroupUseCase.execute(input))
			.isInstanceOf(OptionException.class);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadOptionGroupPort, never())
			.readOrThrow(optionGroupId);

		verify(mockModifyOptionGroupDomainService, never())
			.execute(any());

		verify(mockSaveOptionGroupPort, never())
			.save(any());
	}

}
