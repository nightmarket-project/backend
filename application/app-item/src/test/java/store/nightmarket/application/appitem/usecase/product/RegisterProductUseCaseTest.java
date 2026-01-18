package store.nightmarket.application.appitem.usecase.product;

import static org.mockito.Mockito.*;

import java.math.BigInteger;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.out.SaveProductPort;
import store.nightmarket.application.appitem.usecase.product.dto.RegisterProductUseCaseDto;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.item.valueobject.Name;
import store.nightmarket.domain.item.valueobject.Price;

public class RegisterProductUseCaseTest {

	private RegisterProductUseCase registerProductUseCase;
	private SaveProductPort mockSaveProductPort;

	@BeforeEach
	void setUp() {
		mockSaveProductPort = mock(SaveProductPort.class);
		registerProductUseCase = new RegisterProductUseCase(mockSaveProductPort);
	}

	@Test
	@DisplayName("상품을 등록한다")
	void registerProduct() {
		// given
		RegisterProductUseCaseDto.Input input = RegisterProductUseCaseDto.Input.builder()
			.userId(new UserId(UUID.randomUUID()))
			.name(new Name("상품"))
			.description("상품 설명")
			.price(new Price(BigInteger.valueOf(10000)))
			.build();

		// when
		RegisterProductUseCaseDto.Output output = registerProductUseCase.execute(input);

		// then
		ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor.forClass(Product.class);

		verify(mockSaveProductPort, times(1))
			.save(argumentCaptor.capture());

		Assertions.assertThat(output.productId()).isNotNull();
		Assertions.assertThat(output.productId()).isEqualTo(argumentCaptor.getValue().getProductId());
	}

}
