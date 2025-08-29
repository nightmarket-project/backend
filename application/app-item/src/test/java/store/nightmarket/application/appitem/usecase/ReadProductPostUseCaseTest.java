package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;

class ReadProductPostUseCaseTest {

	private ReadProductPostPort mockReadProductPostPort;
	private ReadProductPostUseCase readProductPostUseCase;

	@BeforeEach
	void setUp() {
		mockReadProductPostPort = mock(ReadProductPostPort.class);
		readProductPostUseCase = new ReadProductPostUseCase(mockReadProductPostPort);
	}

	@Test
	@DisplayName("상품 게시글 조회")
	void readProductPost() {
		// given
		UUID productPostId = UUID.randomUUID();
		UUID productId = UUID.randomUUID();
		ProductPostDto productPostDto = ProductPostDto.builder()
			.product(TestDomainFactory.createProduct(productId))
			.productPost(TestDomainFactory.createProductPost(productPostId, productId))
			.build();

		when(mockReadProductPostPort.readOrThrowFetch(productPostId))
			.thenReturn(productPostDto);

		// when
		ReadProductPostUseCaseDto.Output output = readProductPostUseCase.execute(productPostId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.productPostDto()).isEqualTo(productPostDto);
		verify(mockReadProductPostPort, times(1))
			.readOrThrowFetch(productPostId);
	}

}
