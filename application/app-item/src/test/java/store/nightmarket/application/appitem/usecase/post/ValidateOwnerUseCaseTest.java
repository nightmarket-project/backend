package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPostPort;
import store.nightmarket.application.appitem.out.mapper.dto.ProductPostAdapterDto;
import store.nightmarket.application.appitem.usecase.post.dto.ValidateOwnerUseCaseDto;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;

public class ValidateOwnerUseCaseTest {

	private ValidateOwnerUseCase validateOwnerUseCase;
	private ReadProductPostPort mockReadProductPostPort;

	@BeforeEach
	void setUp() {
		mockReadProductPostPort = mock(ReadProductPostPort.class);
		validateOwnerUseCase = new ValidateOwnerUseCase(
			mockReadProductPostPort
		);
	}

	@Test
	@DisplayName("게시글의의 주인이 아니면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());

		ProductPost productPost = TestDomainFactory.createProductPost(
			productPostId.getId(),
			productId.getId()
		);

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		ProductPostAdapterDto productPostAdapterDto = ProductPostAdapterDto.builder()
			.product(product)
			.productPost(productPost)
			.build();

		when(mockReadProductPostPort.readOrThrowFetch(productPostId))
			.thenReturn(productPostAdapterDto);

		ValidateOwnerUseCaseDto.Input input = ValidateOwnerUseCaseDto.Input.builder()
			.productPostId(productPostId)
			.userId(new UserId(UUID.randomUUID()))
			.build();

		// when
		// then
		assertThatThrownBy(() -> validateOwnerUseCase.execute(input))
			.isInstanceOf(ProductPostException.class);
	}
}
