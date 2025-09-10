package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.dto.ProductPostAdapterDto;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostUseCaseDto;
import store.nightmarket.itemweb.valueobject.ProductPostId;

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
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		UUID productId = UUID.randomUUID();
		ProductPostAdapterDto productPostAdapterDto = ProductPostAdapterDto.builder()
			.product(TestDomainFactory.createProduct(productId))
			.productPost(TestDomainFactory.createProductPost(productPostId.getId(), productId))
			.build();

		when(mockReadProductPostPort.readOrThrowFetch(productPostId))
			.thenReturn(productPostAdapterDto);

		// when
		ReadProductPostUseCaseDto.Output output = readProductPostUseCase.execute(productPostId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.productPostAdapterDto()).isEqualTo(productPostAdapterDto);
		verify(mockReadProductPostPort, times(1))
			.readOrThrowFetch(productPostId);
	}

}
