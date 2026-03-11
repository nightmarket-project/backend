package store.nightmarket.application.appitem.usecase.post;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.usecase.post.dto.UnpublishProductPostUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.PostState;
import store.nightmarket.domain.itemweb.valueobject.Rating;

public class UnpublishProductPostUseCaseTest {

	private UnpublishProductPostUseCase unpublishProductPostUseCase;
	private ReadProductPostPort mockReadProductPostPort;
	private SaveProductPostPort mockSaveProductPostPort;

	@BeforeEach
	void setUp() {
		mockReadProductPostPort = mock(ReadProductPostPort.class);
		mockSaveProductPostPort = mock(SaveProductPostPort.class);
		unpublishProductPostUseCase = new UnpublishProductPostUseCase(
			mockReadProductPostPort,
			mockSaveProductPostPort
		);
	}

	@Test
	@DisplayName("상품 게시글을 내린다")
	void unpublishProductPost() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());

		ProductPost productPost = ProductPost.newInstance(
			productPostId,
			productId,
			new Rating(0.0f),
			PostState.PUBLISHED
		);

		when(mockReadProductPostPort.readOrThrow(productPostId))
			.thenReturn(productPost);

		UnpublishProductPostUseCaseDto.Input input = UnpublishProductPostUseCaseDto.Input.builder()
			.productPostId(productPostId)
			.build();

		// when
		unpublishProductPostUseCase.execute(input);

		// then
		verify(mockReadProductPostPort, times(1))
			.readOrThrow(productPostId);

		ArgumentCaptor<ProductPost> argumentCaptor = ArgumentCaptor.forClass(ProductPost.class);

		verify(mockSaveProductPostPort, times(1))
			.save(argumentCaptor.capture());

		ProductPost savedProductPost = argumentCaptor.getValue();

		Assertions.assertThat(savedProductPost.getState()).isEqualTo(PostState.UNPUBLISHED);
	}

}
