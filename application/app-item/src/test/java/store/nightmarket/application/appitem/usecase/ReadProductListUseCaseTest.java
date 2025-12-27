package store.nightmarket.application.appitem.usecase;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.usecase.dto.ReadProductListUseCaseDto;
import store.nightmarket.domain.item.model.Product;

public class ReadProductListUseCaseTest {

	private ReadProductListUseCase readProductListUseCase;
	private ReadProductPort mockReadProductPort;
	private SoftAssertions softly;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		readProductListUseCase = new ReadProductListUseCase(
			mockReadProductPort
		);
		softly = new SoftAssertions();
	}

	@Test
	@DisplayName("전체 상품을 조회한다")
	void readProductList() {
		// given
		int page = 0;
		int size = 25;

		UUID productIdA = UUID.randomUUID();
		UUID productIdB = UUID.randomUUID();

		ReadProductListUseCaseDto.Input input = ReadProductListUseCaseDto.Input.builder()
			.page(page)
			.size(size)
			.build();

		List<Product> productList = List.of(
			TestDomainFactory.createProduct(productIdA),
			TestDomainFactory.createProduct(productIdB)
		);

		PageImpl<Product> productPage = new PageImpl<>(
			productList,
			PageRequest.of(page, 25),
			productList.size()
		);

		when(mockReadProductPort.readAll(PageRequest.of(0, 25)))
			.thenReturn(productPage);

		// when
		ReadProductListUseCaseDto.Output output = readProductListUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readAll(PageRequest.of(0, 25));

		softly.assertThat(output.productPage().getTotalElements()).isEqualTo(2);
		softly.assertThat(output.productPage().getTotalPages()).isEqualTo(1);
		softly.assertAll();
	}

}