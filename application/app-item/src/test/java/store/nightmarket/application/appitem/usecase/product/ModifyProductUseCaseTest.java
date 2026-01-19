package store.nightmarket.application.appitem.usecase.product;

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
import store.nightmarket.application.appitem.out.SaveProductPort;
import store.nightmarket.application.appitem.usecase.product.dto.ModifyProductUseCaseDto;
import store.nightmarket.domain.item.exception.ProductException;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.service.ModifyProductDomainService;
import store.nightmarket.domain.item.service.dto.ModifyProductDomainServiceDto;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class ModifyProductUseCaseTest {

	private ModifyProductUseCase modifyProductUseCase;
	private ReadProductPort mockReadProductPort;
	private SaveProductPort mockSaveProductPort;
	private ModifyProductDomainService mockModifyProductDomainService;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockSaveProductPort = mock(SaveProductPort.class);
		mockModifyProductDomainService = mock(ModifyProductDomainService.class);
		modifyProductUseCase = new ModifyProductUseCase(
			mockReadProductPort,
			mockSaveProductPort,
			mockModifyProductDomainService
		);
	}

	@Test
	@DisplayName("상품의 내용을 수정한다")
	void modifyProduct() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		Name modifiedName = new Name("modified name");
		String modifiedDescription = "modified description";
		Price modifiedPrice = new Price(BigInteger.valueOf(2000));

		Product modifiedProduct = Product.newInstance(
			productId,
			userId,
			modifiedName,
			modifiedDescription,
			modifiedPrice
		);

		ModifyProductUseCaseDto.Input input = ModifyProductUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.name(modifiedName)
			.description(modifiedDescription)
			.price(modifiedPrice)
			.build();

		ModifyProductDomainServiceDto.Input domianInput = ModifyProductDomainServiceDto.Input.builder()
			.product(product)
			.name(input.name())
			.description(input.description())
			.price(input.price())
			.build();

		ModifyProductDomainServiceDto.Event event = ModifyProductDomainServiceDto.Event.builder()
			.product(modifiedProduct)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockModifyProductDomainService.execute(domianInput))
			.thenReturn(event);

		// when
		modifyProductUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockModifyProductDomainService, times(1))
			.execute(domianInput);

		ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

		verify(mockSaveProductPort, times(1))
			.save(argumentCaptor.capture());

		Product savedProduct = argumentCaptor.getValue();

		Assertions.assertThat(modifiedProduct.getName()).isEqualTo(savedProduct.getName());
		Assertions.assertThat(modifiedProduct.getDescription()).isEqualTo(savedProduct.getDescription());
		Assertions.assertThat(modifiedProduct.getPrice()).isEqualTo(savedProduct.getPrice());
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 수정을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		Name modifiedName = new Name("modified name");
		String modifiedDescription = "modified description";
		Price modifiedPrice = new Price(BigInteger.valueOf(2000));

		ModifyProductUseCaseDto.Input input = ModifyProductUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.name(modifiedName)
			.description(modifiedDescription)
			.price(modifiedPrice)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		assertThatThrownBy(() -> modifyProductUseCase.execute(input))
			.isInstanceOf(ProductException.class);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockModifyProductDomainService, never())
			.execute(any());

		verify(mockSaveProductPort, never())
			.save(any());
	}

}
