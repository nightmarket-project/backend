package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.mapper.dto.ProductPostAdapterDto;
import store.nightmarket.application.appitem.usecase.post.dto.FindProductByKeywordUseCaseDto;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageType;

class FindProductByKeywordUseCaseTest {

	private ReadProductPostPort mockReadProductPostPort;
	private ReadImageManagerPort mockReadImageManagerPort;
	private FindProductByKeywordUseCase findProductByKeywordUseCase;

	@BeforeEach
	void setUp() {
		mockReadProductPostPort = mock(ReadProductPostPort.class);
		mockReadImageManagerPort = mock(ReadImageManagerPort.class);
		findProductByKeywordUseCase = new FindProductByKeywordUseCase(
			mockReadProductPostPort, mockReadImageManagerPort
		);
	}

	@Test
	@DisplayName("키워드별 상품을 조회한다")
	void findProductListByKeyword() {
		// given
		String keyword = "CPU";
		int page = 0;
		int size = 25;

		UUID productPostId1 = UUID.randomUUID();
		UUID productPostId2 = UUID.randomUUID();

		FindProductByKeywordUseCaseDto.Input usecaseInput =
			FindProductByKeywordUseCaseDto.Input.builder()
				.keyword(keyword)
				.page(page)
				.size(size)
				.build();

		List<ProductPostAdapterDto> content = List.of(
			createProductPostAdapterDto(productPostId1),
			createProductPostAdapterDto(productPostId2)
		);

		PageImpl<ProductPostAdapterDto> dtoPage = new PageImpl<>(
			content,
			PageRequest.of(page, 25),
			content.size()
		);

		List<ProductPostId> productPostIdList = dtoPage.stream()
			.map(dto -> dto.getProductPost().getProductPostId())
			.toList();

		List<ImageManager> imageManagerList = List.of(
			createImageManager(1, productPostId1),
			createImageManager(2, productPostId2)
		);

		when(mockReadProductPostPort.findProductPostListByKeyword(keyword, PageRequest.of(page, 25)))
			.thenReturn(dtoPage);
		when(mockReadImageManagerPort.readThumbnailList(productPostIdList))
			.thenReturn(imageManagerList);

		// when
		FindProductByKeywordUseCaseDto.Output output = findProductByKeywordUseCase.execute(usecaseInput);

		// then
		assertThat(output.dtoPage()).isEqualTo(dtoPage);
		assertThat(output.imageManagerList()).isEqualTo(imageManagerList);

		verify(mockReadProductPostPort, times(1))
			.findProductPostListByKeyword(keyword, PageRequest.of(page, 25));
		verify(mockReadImageManagerPort, times(1))
			.readThumbnailList(productPostIdList);
	}

	private ProductPostAdapterDto createProductPostAdapterDto(UUID productPostId) {
		UUID productId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		ProductPost productPost = TestDomainFactory.createProductPost(
			productPostId,
			productId
		);
		Product product = TestDomainFactory.createProduct(productId, userId);

		return ProductPostAdapterDto.builder()
			.productPost(productPost)
			.product(product)
			.build();
	}

	private ImageManager createImageManager(
		int displayOrder,
		UUID ImageOwnerId
	) {
		return TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			ImageType.THUMBNAIL,
			displayOrder,
			ImageOwnerId
		);
	}

}
