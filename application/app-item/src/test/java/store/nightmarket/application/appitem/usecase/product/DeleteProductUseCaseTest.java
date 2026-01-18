package store.nightmarket.application.appitem.usecase.product;

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
import store.nightmarket.application.appitem.out.DeleteProductPort;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.ReadOptionGroupPort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadVariantOptionValuePort;
import store.nightmarket.application.appitem.usecase.product.dto.DeleteProductUseCaseDto;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.OptionGroupId;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteProductUseCaseTest {

	private DeleteProductUseCase deleteProductUseCase;
	private ReadProductPort mockReadProductPort;
	private ReadVariantOptionValuePort mockReadVariantOptionValuePort;
	private ReadOptionGroupPort mockReadOptionGroupPort;
	private DeleteProductPort mockDeleteProductPort;
	private DeleteOptionGroupPort mockDeleteOptionGroupPort;
	private DeleteOptionValuePort mockDeleteOptionValuePort;
	private DeleteProductVariantPort mockDeleteProductVariantPort;
	private DeleteVariantOptionValuePort mockDeleteVariantOptionValuePort;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadVariantOptionValuePort = mock(ReadVariantOptionValuePort.class);
		mockReadOptionGroupPort = mock(ReadOptionGroupPort.class);
		mockDeleteProductVariantPort = mock(DeleteProductVariantPort.class);
		mockDeleteVariantOptionValuePort = mock(DeleteVariantOptionValuePort.class);
		mockDeleteOptionValuePort = mock(DeleteOptionValuePort.class);
		mockDeleteOptionGroupPort = mock(DeleteOptionGroupPort.class);
		mockDeleteProductPort = mock(DeleteProductPort.class);
		deleteProductUseCase = new DeleteProductUseCase(
			mockReadProductPort,
			mockReadVariantOptionValuePort,
			mockReadOptionGroupPort,
			mockDeleteProductVariantPort,
			mockDeleteVariantOptionValuePort,
			mockDeleteOptionValuePort,
			mockDeleteOptionGroupPort,
			mockDeleteProductPort
		);
	}

	@Test
	@DisplayName("상품 삭제")
	void deleteProduct() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteProductUseCaseDto.Input input = DeleteProductUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.build();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		ProductVariantId productVariantId1 = new ProductVariantId(UUID.randomUUID());
		ProductVariantId productVariantId2 = new ProductVariantId(UUID.randomUUID());

		List<ProductVariantId> productVariantIdList = List.of(productVariantId1, productVariantId2);

		OptionGroupId optionGroupId1 = new OptionGroupId(UUID.randomUUID());
		OptionGroupId optionGroupId2 = new OptionGroupId(UUID.randomUUID());

		List<OptionGroupId> optionGroupIdList = List.of(optionGroupId1, optionGroupId2);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadVariantOptionValuePort.readProductVariantIdsByProductId(productId))
			.thenReturn(productVariantIdList);

		when(mockReadOptionGroupPort.readOptionGroupIdsByProductId(productId))
			.thenReturn(optionGroupIdList);

		// when
		deleteProductUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadVariantOptionValuePort, times(1))
			.readProductVariantIdsByProductId(productId);

		verify(mockReadOptionGroupPort, times(1))
			.readOptionGroupIdsByProductId(productId);

		verify(mockDeleteProductVariantPort, times(1))
			.deleteAll(productVariantIdList);

		verify(mockDeleteVariantOptionValuePort, times(1))
			.deleteAllByProductVariantIdList(productVariantIdList);

		verify(mockDeleteOptionValuePort, times(1))
			.deleteAllByOptionGroupId(optionGroupIdList);

		verify(mockDeleteOptionGroupPort, times(1))
			.deleteAll(optionGroupIdList);

		verify(mockDeleteProductPort, times(1))
			.delete(productId);
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 삭제를 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteProductUseCaseDto.Input input = DeleteProductUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.build();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> deleteProductUseCase.execute(input))
			.isInstanceOf(ProductException.class);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadVariantOptionValuePort, never())
			.readProductVariantIdsByProductId(productId);

		verify(mockReadOptionGroupPort, never())
			.readOptionGroupIdsByProductId(productId);

		verify(mockDeleteProductVariantPort, never())
			.deleteAll(any());

		verify(mockDeleteVariantOptionValuePort, never())
			.deleteAllByProductVariantIdList(any());

		verify(mockDeleteOptionValuePort, never())
			.deleteAllByOptionGroupId(any());

		verify(mockDeleteOptionGroupPort, never())
			.deleteAll(any());

		verify(mockDeleteProductPort, never())
			.delete(productId);
	}

}
