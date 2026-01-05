package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.post.ReadReviewPort;
import store.nightmarket.application.appitem.out.post.mapper.dto.ReplyAdapterDto;
import store.nightmarket.application.appitem.out.post.mapper.dto.ReviewAdapterDto;
import store.nightmarket.application.appitem.usecase.post.dto.ReadReviewUseCaseDto;
import store.nightmarket.itemweb.model.id.ProductPostId;

class ReadReviewUseCaseTest {

	private ReadReviewUseCase readReviewUseCase;
	private ReadReviewPort mockReadReviewPort;

	@BeforeEach
	void setUp() {
		mockReadReviewPort = mock(ReadReviewPort.class);
		readReviewUseCase = new ReadReviewUseCase(mockReadReviewPort);
	}

	@Test
	@DisplayName("리뷰 조회")
	void readReviewList() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		UUID reviewId = UUID.randomUUID();
		UUID replyId = UUID.randomUUID();
		UUID user1Id = UUID.randomUUID();
		UUID user2Id = UUID.randomUUID();

		ReviewAdapterDto reviewAdapterDto = ReviewAdapterDto.builder()
			.review(TestDomainFactory.createReview(reviewId, productPostId.getId(), user1Id))
			.replyAdapterDto(
				ReplyAdapterDto.builder()
					.reply(TestDomainFactory.createReply(replyId, user2Id, reviewId))
					.user(TestDomainFactory.createUser(user2Id))
					.build())
			.user(TestDomainFactory.createUser(user1Id))
			.build();

		when(mockReadReviewPort.read(productPostId))
			.thenReturn(List.of(reviewAdapterDto));

		// when
		ReadReviewUseCaseDto.Output output = readReviewUseCase.execute(productPostId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.reviewAdapterDtoList().size()).isEqualTo(1);
		assertThat(output.reviewAdapterDtoList().getFirst()).isEqualTo(reviewAdapterDto);
		verify(mockReadReviewPort, times(1)).read(productPostId);
	}

}
