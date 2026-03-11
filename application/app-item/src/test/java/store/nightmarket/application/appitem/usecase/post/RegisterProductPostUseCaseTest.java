package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.usecase.post.dto.RegisterProductPostUseCaseDto;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.ProductPost;

public class RegisterProductPostUseCaseTest {

	private RegisterProductPostUseCase registerProductPostUseCase;
	private ReadProductPort mockReadProductPort;
	private SaveProductPostPort mockSaveProductPostPort;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockSaveProductPostPort = mock(SaveProductPostPort.class);
		registerProductPostUseCase = new RegisterProductPostUseCase(
			mockReadProductPort,
			mockSaveProductPostPort
		);
	}

	@Test
	@DisplayName("상품게시글을 등록한다")
	void registerProductPost() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		RegisterProductPostUseCaseDto.Input input = RegisterProductPostUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		registerProductPostUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockSaveProductPostPort, times(1))
			.save(any(ProductPost.class));
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 등록을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		RegisterProductPostUseCaseDto.Input input = RegisterProductPostUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> registerProductPostUseCase.execute(input))
			.isInstanceOf(ProductPostException.class);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockSaveProductPostPort, never())
			.save(any(ProductPost.class));
	}

}
