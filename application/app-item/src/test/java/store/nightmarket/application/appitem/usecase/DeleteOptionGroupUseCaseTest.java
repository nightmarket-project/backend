package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.DeleteOptionGroupPort;
import store.nightmarket.application.appitem.out.DeleteOptionValuePort;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadVariantOptionValuePort;
import store.nightmarket.application.appitem.usecase.dto.DeleteOptionGroupUseCaseDto;
import store.nightmarket.domain.item.exception.OptionException;
import store.nightmarket.domain.item.model.OptionGroup;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteOptionGroupUseCaseTest {

	private DeleteOptionGroupUseCase deleteOptionGroupUseCase;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private ReadProductPort mockReadProductPort;
	private ReadVariantOptionValuePort mockReadVariantOptionValuePort;
	private DeleteOptionGroupPort mockDeleteOptionGroupPort;
	private DeleteVariantOptionValuePort mockDeleteVariantOptionValuePort;
	private DeleteProductVariantPort mockDeleteProductVariantPort;
	private DeleteOptionValuePort mockDeleteOptionValuePort;

	@BeforeEach
	void setUp() {
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadVariantOptionValuePort = mock(ReadVariantOptionValuePort.class);
		mockDeleteOptionGroupPort = mock(DeleteOptionGroupPort.class);
		mockDeleteVariantOptionValuePort = mock(DeleteVariantOptionValuePort.class);
		mockDeleteProductVariantPort = mock(DeleteProductVariantPort.class);
		mockDeleteOptionValuePort = mock(DeleteOptionValuePort.class);
		deleteOptionGroupUseCase = new DeleteOptionGroupUseCase(
			mockReadOptionGroupPort,
			mockReadProductPort,
			mockReadVariantOptionValuePort,
			mockDeleteOptionGroupPort,
			mockDeleteVariantOptionValuePort,
			mockDeleteProductVariantPort,
			mockDeleteOptionValuePort
		);
	}

	@Test
	@DisplayName("옵션 그룹 삭제")
	void deleteOptionGroup() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionGroupUseCaseDto.Input input = DeleteOptionGroupUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.userId(userId)
			.build();

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

		when(mockReadOptionGroupPort.readOrThrow(optionGroupId))
			.thenReturn(optionGroup);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadVariantOptionValuePort.readProductVariantIdsByOptionGroupId(optionGroupId))
			.thenReturn(productVariantIdList);

		// when
		deleteOptionGroupUseCase.execute(input);

		// then
		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadVariantOptionValuePort, times(1))
			.readProductVariantIdsByOptionGroupId(optionGroupId);

		verify(mockDeleteVariantOptionValuePort, times(1))
			.deleteByOptionGroupId(optionGroupId);

		verify(mockDeleteOptionValuePort, times(1))
			.deleteByOptionGroupId(optionGroupId);

		verify(mockDeleteOptionGroupPort, times(1))
			.deleteById(optionGroupId);

		//verify(mockDeleteProductVariantPort, times(1))
		//	.deleteAll(List.of(productVariantId1, productVariantId2));
		/// TO-DO 추후 수정시 변경
	}

	@Test
	@DisplayName("옵션 그룹의 주인이 아닌 사람이 삭제를 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		OptionGroupId optionGroupId = new OptionGroupId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteOptionGroupUseCaseDto.Input input = DeleteOptionGroupUseCaseDto.Input.builder()
			.productId(productId)
			.optionGroupId(optionGroupId)
			.userId(userId)
			.build();

		OptionGroup optionGroup = TestDomainFactory.createOptionGroup(
			optionGroupId.getId(),
			productId.getId()
		);

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		when(mockReadOptionGroupPort.readOrThrow(any()))
			.thenReturn(optionGroup);

		when(mockReadProductPort.readOrThrow(any()))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> deleteOptionGroupUseCase.execute(input))
			.isInstanceOf(OptionException.class);

		verify(mockReadOptionGroupPort, times(1))
			.readOrThrow(optionGroupId);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadVariantOptionValuePort, never())
			.readProductVariantIdsByOptionGroupId(optionGroupId);

		verify(mockDeleteVariantOptionValuePort, never())
			.deleteByOptionGroupId(any());

		verify(mockDeleteOptionValuePort, never())
			.deleteByOptionGroupId(optionGroupId);

		verify(mockDeleteOptionGroupPort, never())
			.deleteById(optionGroupId);

		verify(mockDeleteProductVariantPort, never())
			.deleteAll(any());
	}

}
