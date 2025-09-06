package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadReviewPort;
import store.nightmarket.application.appitem.out.dto.ReplyDto;
import store.nightmarket.application.appitem.out.dto.ReviewDto;
import store.nightmarket.application.appitem.usecase.dto.ReadReviewUseCaseDto;

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
		UUID productPostId = UUID.randomUUID();
		UUID reviewId = UUID.randomUUID();
		UUID replyId = UUID.randomUUID();
		UUID user1Id = UUID.randomUUID();
		UUID user2Id = UUID.randomUUID();

		ReviewDto reviewDto = ReviewDto.builder()
			.review(TestDomainFactory.createReview(reviewId, productPostId, user1Id))
			.replyDto(
				ReplyDto.builder()
					.reply(TestDomainFactory.createReply(replyId, user2Id, reviewId))
					.user(TestDomainFactory.createUser(user2Id))
					.build())
			.user(TestDomainFactory.createUser(user1Id))
			.build();

		when(mockReadReviewPort.read(productPostId))
			.thenReturn(List.of(reviewDto));

		// when
		ReadReviewUseCaseDto.Output output = readReviewUseCase.execute(productPostId);

		// then
		assertThat(output).isNotNull();
		assertThat(output.reviewDtoList().size()).isEqualTo(1);
		assertThat(output.reviewDtoList().getFirst()).isEqualTo(reviewDto);
		verify(mockReadReviewPort, times(1)).read(productPostId);
	}

}
