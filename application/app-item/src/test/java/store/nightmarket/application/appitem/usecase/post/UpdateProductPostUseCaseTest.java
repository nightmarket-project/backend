package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.usecase.post.dto.UpdateProductPostUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.PostState;
import store.nightmarket.domain.itemweb.valueobject.Rating;

public class UpdateProductPostUseCaseTest {

	private UpdateProductPostUseCase updateProductPostUseCase;
	private ReadProductPostPort mockReadProductPostPort;
	private SaveProductPostPort mockSaveProductPostPort;

	@BeforeEach
	void setUp() {
		mockReadProductPostPort = mock(ReadProductPostPort.class);
		mockSaveProductPostPort = mock(SaveProductPostPort.class);
		updateProductPostUseCase = new UpdateProductPostUseCase(
			mockReadProductPostPort,
			mockSaveProductPostPort
		);
	}

	@Test
	@DisplayName("상품 게시글을 수정한다")
	void publishProductPost() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());

		ProductPost productPost = TestDomainFactory.createProductPost(
			productPostId.getId(),
			productId.getId()
		);

		when(mockReadProductPostPort.readOrThrow(productPostId))
			.thenReturn(productPost);

		UpdateProductPostUseCaseDto.Input input = UpdateProductPostUseCaseDto.Input.builder()
			.productPostId(productPostId)
			.rating(new Rating(3.0f))
			.build();

		// when
		updateProductPostUseCase.execute(input);

		// then
		verify(mockReadProductPostPort, times(1))
			.readOrThrow(productPostId);

		ArgumentCaptor<ProductPost> argumentCaptor = ArgumentCaptor.forClass(ProductPost.class);

		verify(mockSaveProductPostPort, times(1))
			.save(argumentCaptor.capture());

		ProductPost savedProductPost = argumentCaptor.getValue();

		assertSoftly(softly -> {
			softly.assertThat(savedProductPost.getState()).isEqualTo(PostState.UNPUBLISHED);
			softly.assertThat(savedProductPost.getRating().value()).isEqualTo(3.0f);
		});
	}

}
