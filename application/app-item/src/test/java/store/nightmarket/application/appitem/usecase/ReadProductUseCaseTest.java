package store.nightmarket.application.appitem.usecase;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.usecase.dto.ReadProductUseCaseDto;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;

public class ReadProductUseCaseTest {

	private ReadProductUseCase readProductUseCase;
	private ReadProductPort mockReadProductPort;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		readProductUseCase = new ReadProductUseCase(
			mockReadProductPort
		);
	}

	@Test
	@DisplayName("단일 상품을 조회한다")
	void readProduct() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());

		ReadProductUseCaseDto.Input input = ReadProductUseCaseDto.Input.builder()
			.productId(productId)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(any(Product.class));

		// when
		readProductUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(any(ProductId.class));
	}

}
