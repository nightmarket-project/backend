package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.post.ReadImageManagerPort;
import store.nightmarket.application.appitem.usecase.post.dto.ReadImageManagerListUseCaseDto;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ReviewId;
import store.nightmarket.domain.itemweb.model.state.ImageType;

class ReadImageManagerListUseCaseTest {

	private ReadImageManagerListUseCase readImageManagerListUseCase;
	private ReadImageManagerPort mockReadImageManagerPort;

	@BeforeEach
	void setUp() {
		mockReadImageManagerPort = Mockito.mock(ReadImageManagerPort.class);
		readImageManagerListUseCase = new ReadImageManagerListUseCase(mockReadImageManagerPort);
	}

	@Test
	@DisplayName("리뷰 아이디 리스트를 받아서 리뷰 이미지를 조회한다.")
	void readReviewImageListWithReviewIdList() {
		// given
		ReviewId reviewId1 = new ReviewId(UUID.randomUUID());
		ReviewId reviewId2 = new ReviewId(UUID.randomUUID());
		ImageManager imageManager1 = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			ImageType.MAIN,
			1,
			reviewId1.getId()
		);
		ImageManager imageManager2 = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			ImageType.MAIN,
			1,
			reviewId2.getId()
		);

		ReadImageManagerListUseCaseDto.Input input = ReadImageManagerListUseCaseDto.Input.builder()
			.idList(List.of(reviewId1, reviewId2))
			.build();
		when(mockReadImageManagerPort.readIdList(List.of(reviewId1, reviewId2)))
			.thenReturn(List.of(imageManager1, imageManager2));

		// when
		ReadImageManagerListUseCaseDto.Output output = readImageManagerListUseCase.execute(input);

		// then
		assertThat(output).isNotNull();
		assertThat(output.imageManagerList().size())
			.isEqualTo(2);
		assertThat(output.imageManagerList())
			.isEqualTo(List.of(imageManager1, imageManager2));
		verify(mockReadImageManagerPort, times(1))
			.readIdList(List.of(reviewId1, reviewId2));
	}

}
