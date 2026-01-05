package store.nightmarket.application.appitem.usecase.option;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.option.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.option.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.option.ReadOptionValuePort;
import store.nightmarket.application.appitem.out.product.ReadProductPort;
import store.nightmarket.application.appitem.out.variant.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.variant.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.variant.ReadVariantOptionValuePort;
import store.nightmarket.application.appitem.usecase.option.dto.DeleteOptionValueUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.OptionValue;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.OptionValueId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteOptionValueUseCaseTest {

	private DeleteOptionValueUseCase deleteOptionValueUseCase;
	private ReadOptionValuePort mockReadOptionValuePort;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private ReadProductPort mockReadProductPort;
	private ReadVariantOptionValuePort mockReadVariantOptionValuePort;
	private DeleteOptionValuePort mockDeleteOptionValuePort;
	private DeleteVariantOptionValuePort mockDeleteVariantOptionValuePort;
	private DeleteProductVariantPort mockDeleteProductVariantPort;

	@BeforeEach
	void setUp() {
		mockReadOptionValuePort = mock(ReadOptionValuePort.class);
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadVariantOptionValuePort = mock(ReadVariantOptionValuePort.class);
		mockDeleteVariantOptionValuePort = mock(DeleteVariantOptionValuePort.class);
		mockDeleteProductVariantPort = mock(DeleteProductVariantPort.class);
		mockDeleteOptionValuePort = mock(DeleteOptionValuePort.class);
		deleteOptionValueUseCase = new DeleteOptionValueUseCase(
			mockReadOptionValuePort,
			mockReadOptionGroupPort,
			mockReadProductPort,
			mockReadVariantOptionValuePort,
			mockDeleteOptionValuePort,
			mockDeleteVariantOptionValuePort,
			mockDeleteProductVariantPort
		);
	}

	@Test
	@DisplayName("옵션 밸류 삭제")
	void deleteOptionValue() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		OptionValueId optionValueId = new OptionValueId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionValueUseCaseDto.Input input = DeleteOptionValueUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.optionValueId(optionValueId)
			.userId(userId)
			.build();

		OptionValue optionValue = TestDomainFactory.createOptionValue(
			optionValueId.getId(),
			optionGroupId.getId()
		);

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		ProductVariantId productVariantId1 = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantId2 = new ProductVariantId(UUID.randomUUID());

		List<ProductVariantId> productVariantIdList = List.of(productVariantId1, productVariantId2);

		when(mockReadOptionValuePort.readOrThrow(any()))
			.thenReturn(optionValue);

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadVariantOptionValuePort.readProductVariantIdsByOptionValueId(optionValueId))
			.thenReturn(productVariantIdList);

		// when
		deleteOptionValueUseCase.execute(input);

		// then
		verify(mockReadOptionValuePort, times(1))
			.readOrThrow(optionValueId);

		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadVariantOptionValuePort, times(1))
			.readProductVariantIdsByOptionValueId(optionValueId);

		verify(mockDeleteVariantOptionValuePort, times(1))
			.deleteByOptionValueId(optionValueId);

		verify(mockDeleteOptionValuePort, times(1))
			.delete(optionValueId);

		//verify(mockDeleteProductVariantPort, times(1))
		//	.deleteAll(productVariantIdList);
		/// TO-DO 추후 수정시 변경
	}

	@Test
	@DisplayName("옵션 밸류의 주인이 아닌 사람이 삭제를 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		OptionValueId optionValueId = new OptionValueId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionValueUseCaseDto.Input input = DeleteOptionValueUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.optionValueId(optionValueId)
			.userId(userId)
			.build();

		OptionValue optionValue = TestDomainFactory.createOptionValue(
			optionValueId.getId(),
			optionGroupId.getId()
		);

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		ProductVariantId productVariantId1 = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantId2 = new ProductVariantId(UUID.randomUUID());

		List<ProductVariantId> productVariantIdList = List.of(productVariantId1, productVariantId2);

		when(mockReadOptionValuePort.readOrThrow(any()))
			.thenReturn(optionValue);

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> deleteOptionValueUseCase.execute(input))
			.isInstanceOf(OptionException.class);

		verify(mockReadOptionValuePort, times(1))
			.readOrThrow(optionValueId);

		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadVariantOptionValuePort, never())
			.readProductVariantIdsByOptionValueId(optionValueId);

		verify(mockDeleteVariantOptionValuePort, never())
			.deleteByOptionValueId(optionValueId);

		verify(mockDeleteOptionValuePort, never())
			.delete(optionValueId);

		verify(mockDeleteProductVariantPort, never())
			.deleteAll(productVariantIdList);
	}

}
