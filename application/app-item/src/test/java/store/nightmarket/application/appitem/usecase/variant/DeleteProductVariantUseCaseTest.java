package store.nightmarket.application.appitem.usecase.variant;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.DeleteProductVariantPort;
import store.nightmarket.application.appitem.out.DeleteVariantOptionValuePort;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.usecase.variant.dto.DeleteProductVariantUseCaseDto;
import store.nightmarket.domain.item.exception.ProductVariantException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;

public class DeleteProductVariantUseCaseTest {

	private DeleteProductVariantUseCase deleteProductVariantUseCase;
	private ReadProductVariantPort mockReadProductVariantPort;
	private ReadProductPort mockReadProductPort;
	private DeleteProductVariantPort mockDeleteProductVariantPort;
	private DeleteVariantOptionValuePort mockDeleteVariantOptionValuePort;

	@BeforeEach
	void setUp() {
		mockReadProductVariantPort = mock(ReadProductVariantPort.class);
		mockReadProductPort = mock(ReadProductPort.class);
		mockDeleteProductVariantPort = mock(DeleteProductVariantPort.class);
		mockDeleteVariantOptionValuePort = mock(DeleteVariantOptionValuePort.class);
		deleteProductVariantUseCase = new DeleteProductVariantUseCase(
			mockReadProductVariantPort,
			mockReadProductPort,
			mockDeleteProductVariantPort,
			mockDeleteVariantOptionValuePort
		);
	}

	@Test
	@DisplayName("ProductVariant를 삭제한다")
	void deleteProductVariant() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteProductVariantUseCaseDto.Input input = DeleteProductVariantUseCaseDto.Input.builder()
			.productId(productId)
			.productVariantId(productVariantId)
			.userId(userId)
			.build();

		ProductVariant productVariant = TestDomainFactory.createProductVariant(
			productVariantId.getId(),
			productId.getId()
		);

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		when(mockReadProductVariantPort.readOrThrow(productVariantId))
			.thenReturn(productVariant);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		deleteProductVariantUseCase.execute(input);

		// then
		verify(mockReadProductVariantPort, times(1))
			.readOrThrow(productVariantId);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockDeleteProductVariantPort, times(1))
			.delete(productVariantId);

		verify(mockDeleteVariantOptionValuePort, times(1))
			.deleteByProductVariantId(productVariantId);
	}

	@Test
	@DisplayName("ProductVariant의 주인이 아닌 사람이 삭제를 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		DeleteProductVariantUseCaseDto.Input input = DeleteProductVariantUseCaseDto.Input.builder()
			.productId(productId)
			.productVariantId(productVariantId)
			.userId(userId)
			.build();

		ProductVariant productVariant = TestDomainFactory.createProductVariant(
			productVariantId.getId(),
			productId.getId()
		);

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		when(mockReadProductVariantPort.readOrThrow(productVariantId))
			.thenReturn(productVariant);

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> deleteProductVariantUseCase.execute(input))
			.isInstanceOf(ProductVariantException.class);

		verify(mockReadProductVariantPort, times(1))
			.readOrThrow(productVariantId);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockDeleteProductVariantPort, never())
			.delete(productVariantId);

		verify(mockDeleteVariantOptionValuePort, never())
			.deleteByProductVariantId(productVariantId);
	}

}
