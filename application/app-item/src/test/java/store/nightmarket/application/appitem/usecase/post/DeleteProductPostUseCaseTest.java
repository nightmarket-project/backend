package store.nightmarket.application.appitem.usecase.post;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.usecase.post.dto.DeleteProductPostUseCaseDto;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.PostState;

public class DeleteProductPostUseCaseTest {

	private DeleteProductPostUseCase deleteProductPostUseCase;
	private ReadProductPostPort mockReadProductPostPort;
	private SaveProductPostPort mockSaveProductPostPort;

	@BeforeEach
	void setUp() {
		mockReadProductPostPort = mock(ReadProductPostPort.class);
		mockSaveProductPostPort = mock(SaveProductPostPort.class);
		deleteProductPostUseCase = new DeleteProductPostUseCase(
			mockReadProductPostPort,
			mockSaveProductPostPort
		);
	}

	@Test
	@DisplayName("상품 게시글을 삭제한다")
	void deleteProductPost() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());

		ProductPost productPost = TestDomainFactory.createProductPost(
			productPostId.getId(),
			productId.getId()
		);

		when(mockReadProductPostPort.readOrThrow(productPostId))
			.thenReturn(productPost);

		DeleteProductPostUseCaseDto.Input input = DeleteProductPostUseCaseDto.Input.builder()
			.productPostId(productPostId)
			.build();

		// when
		deleteProductPostUseCase.execute(input);

		// then
		verify(mockReadProductPostPort, times(1))
			.readOrThrow(productPostId);

		ArgumentCaptor<ProductPost> argumentCaptor = ArgumentCaptor.forClass(ProductPost.class);

		verify(mockSaveProductPostPort, times(1))
			.save(argumentCaptor.capture());

		ProductPost savedProductPost = argumentCaptor.getValue();

		Assertions.assertThat(savedProductPost.getState()).isEqualTo(PostState.DELETED);
	}

}
