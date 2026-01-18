package store.nightmarket.application.appitem.usecase.variant;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.ReadProductVariantPort;
import store.nightmarket.application.appitem.out.SaveProductVariantPort;
import store.nightmarket.application.appitem.usecase.variant.dto.ModifyProductVariantUseCaseDto;
import store.nightmarket.domain.item.exception.ProductVariantException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.ProductVariant;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.ProductVariantId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.service.ModifyProductVariantDomainService;
import store.nightmarket.domain.item.service.dto.ModifyProductVariantDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Quantity;

public class ModifyProductVariantUseCaseTest {

	private ModifyProductVariantUseCase modifyProductVariantUseCase;
	private ReadProductPort mockReadProductPort;
	private ReadProductVariantPort mockReadProductVariantPort;
	private SaveProductVariantPort mockSaveProductVariantPort;
	private ModifyProductVariantDomainService mockModifyProductVariantDomainService;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockReadProductVariantPort = mock(ReadProductVariantPort.class);
		mockSaveProductVariantPort = mock(SaveProductVariantPort.class);
		mockModifyProductVariantDomainService = mock(ModifyProductVariantDomainService.class);
		modifyProductVariantUseCase = new ModifyProductVariantUseCase(
			mockReadProductPort,
			mockReadProductVariantPort,
			mockSaveProductVariantPort,
			mockModifyProductVariantDomainService
		);
	}

	@Test
	@DisplayName("ProductVariant의 내용을 수정한다")
	void modifyProductVariant() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		ProductVariant productVariant = TestDomainFactory.createProductVariant(
			productVariantId.getId(),
			productId.getId()
		);

		String modifiedSKUCode = "modified SKUCode";
		Quantity modifiedQuantity = new Quantity(BigInteger.valueOf(2));

		ProductVariant modifiedProductVariant = ProductVariant.newInstance(
			productVariantId,
			productId,
			userId,
			modifiedSKUCode,
			modifiedQuantity
		);

		ModifyProductVariantUseCaseDto.Input input = ModifyProductVariantUseCaseDto.Input.builder()
			.productId(productId)
			.productVariantId(productVariantId)
			.userId(userId)
			.SKUCode(modifiedSKUCode)
			.quantity(modifiedQuantity)
			.build();

		ModifyProductVariantDomainServiceDto.Input domainInput = ModifyProductVariantDomainServiceDto.Input.builder()
			.productVariant(productVariant)
			.SKUCode(input.SKUCode())
			.quantity(input.quantity())
			.build();

		ModifyProductVariantDomainServiceDto.Event event = ModifyProductVariantDomainServiceDto.Event.builder()
			.productVariant(modifiedProductVariant)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockReadProductVariantPort.readOrThrow(productVariantId))
			.thenReturn(productVariant);

		when(mockModifyProductVariantDomainService.execute(domainInput))
			.thenReturn(event);

		// when
		modifyProductVariantUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadProductVariantPort, times(1))
			.readOrThrow(productVariantId);

		verify(mockModifyProductVariantDomainService, times(1))
			.execute(domainInput);

		ArgumentCaptor<ProductVariant> argumentCaptor = ArgumentCaptor.forClass(ProductVariant.class);

		verify(mockSaveProductVariantPort, times(1))
			.save(argumentCaptor.capture());

		ProductVariant saveProductVariant = argumentCaptor.getValue();

		Assertions.assertThat(modifiedProductVariant.getSKUCode()).isEqualTo(saveProductVariant.getSKUCode());
		Assertions.assertThat(modifiedProductVariant.getQuantity()).isEqualTo(saveProductVariant.getQuantity());
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 수정을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		ProductVariantId productVariantId = new ProductVariantId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		String modifiedSKUCode = "modified SKUCode";
		Quantity modifiedQuantity = new Quantity(BigInteger.valueOf(2));

		ModifyProductVariantUseCaseDto.Input input = ModifyProductVariantUseCaseDto.Input.builder()
			.productId(productId)
			.productVariantId(productVariantId)
			.userId(userId)
			.SKUCode(modifiedSKUCode)
			.quantity(modifiedQuantity)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> modifyProductVariantUseCase.execute(input))
			.isInstanceOf(ProductVariantException.class);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockReadProductVariantPort, never())
			.readOrThrow(productVariantId);

		verify(mockModifyProductVariantDomainService, never())
			.execute(any());

		verify(mockSaveProductVariantPort, never())
			.save(any());
	}

}
