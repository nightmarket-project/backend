package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadProductPort;
import store.nightmarket.application.appitem.out.SaveProductPostPort;
import store.nightmarket.application.appitem.usecase.post.dto.RegisterAndScheduleProductPostUseCaseDto;
import store.nightmarket.domain.item.model.Product;
import store.nightmarket.domain.item.model.id.ProductId;
import store.nightmarket.domain.item.model.id.UserId;
import store.nightmarket.domain.itemweb.exception.ProductPostException;
import store.nightmarket.domain.itemweb.model.ProductPost;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.service.ScheduleProductPostDomainService;
import store.nightmarket.domain.itemweb.service.dto.ScheduleProductPostDomainServiceDto;

public class RegisterAndScheduleProductPostUseCaseTest {

	private RegisterAndScheduleProductPostUseCase registerAndScheduleProductPostUseCase;
	private ReadProductPort mockReadProductPort;
	private SaveProductPostPort mockSaveProductPostPort;
	private ScheduleProductPostDomainService mockScheduleProductPostDomainService;

	@BeforeEach
	void setUp() {
		mockReadProductPort = mock(ReadProductPort.class);
		mockSaveProductPostPort = mock(SaveProductPostPort.class);
		mockScheduleProductPostDomainService = mock(ScheduleProductPostDomainService.class);
		registerAndScheduleProductPostUseCase = new RegisterAndScheduleProductPostUseCase(
			mockReadProductPort,
			mockSaveProductPostPort,
			mockScheduleProductPostDomainService
		);
	}

	@Test
	@DisplayName("상품에 대한 게시글을 생성하고 예약한다")
	void registerAndSchedule() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());
		LocalDateTime now = LocalDateTime.now();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			userId.getId()
		);

		ProductPost productPost = TestDomainFactory.createProductPost(
			productPostId.getId(),
			productId.getId()
		);

		RegisterAndScheduleProductPostUseCaseDto.Input input = RegisterAndScheduleProductPostUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.publishAt(now.plusDays(10))
			.expiredAt(now.plusDays(20))
			.build();

		ScheduleProductPostDomainServiceDto.Event event = ScheduleProductPostDomainServiceDto.Event.builder()
			.productPost(productPost)
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		when(mockScheduleProductPostDomainService.execute(any(ScheduleProductPostDomainServiceDto.Input.class)))
			.thenReturn(event);

		// when
		registerAndScheduleProductPostUseCase.execute(input);

		// then
		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockScheduleProductPostDomainService, times(1))
			.execute(any(ScheduleProductPostDomainServiceDto.Input.class));

		verify(mockSaveProductPostPort, times(1))
			.save(any(ProductPost.class));
	}

	@Test
	@DisplayName("상품의 주인이 아닌 사람이 게시글 예약을 하면 예외를 던진다")
	void shouldThrowExceptionWhenNotOwner() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ProductId productId = new ProductId(UUID.randomUUID());
		UserId userId = new UserId(UUID.randomUUID());
		LocalDateTime now = LocalDateTime.now();

		Product product = TestDomainFactory.createProduct(
			productId.getId(),
			UUID.randomUUID()
		);

		ProductPost productPost = TestDomainFactory.createProductPost(
			productPostId.getId(),
			productId.getId()
		);

		RegisterAndScheduleProductPostUseCaseDto.Input input = RegisterAndScheduleProductPostUseCaseDto.Input.builder()
			.productId(productId)
			.userId(userId)
			.publishAt(now.plusDays(10))
			.expiredAt(now.plusDays(20))
			.build();

		when(mockReadProductPort.readOrThrow(productId))
			.thenReturn(product);

		// when
		// then
		assertThatThrownBy(() -> registerAndScheduleProductPostUseCase.execute(input))
			.isInstanceOf(ProductPostException.class);

		verify(mockReadProductPort, times(1))
			.readOrThrow(productId);

		verify(mockScheduleProductPostDomainService, never())
			.execute(any(ScheduleProductPostDomainServiceDto.Input.class));

		verify(mockSaveProductPostPort, never())
			.save(any(ProductPost.class));
	}

}
