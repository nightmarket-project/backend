package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.usecase.dto.ReadReviewImageUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;

class ReadReviewImageUseCaseTest {

	private ReadReviewImageUseCase readReviewImageUseCase;
	private ReadImageManagerPort mockReadImageManagerPort;

	@BeforeEach
	void setUp() {
		mockReadImageManagerPort = Mockito.mock(ReadImageManagerPort.class);
		readReviewImageUseCase = new ReadReviewImageUseCase(mockReadImageManagerPort);
	}

	@Test
	@DisplayName("리뷰 아이디 리스트를 받아서 리뷰 이미지를 조회한다.")
	void readReviewImageListWithReviewIdList() {
		// given
		UUID review1Id = UUID.randomUUID();
		UUID review2Id = UUID.randomUUID();
		ImageManager imageManager1 = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			DomainImageType.MAIN,
			1,
			review1Id
		);
		ImageManager imageManager2 = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			DomainImageType.MAIN,
			1,
			review2Id
		);

		ReadReviewImageUseCaseDto.Input input = ReadReviewImageUseCaseDto.Input.builder()
			.idList(List.of(review1Id, review2Id))
			.build();
		when(mockReadImageManagerPort.readIdList(List.of(review1Id, review2Id)))
			.thenReturn(List.of(imageManager1, imageManager2));

		// when
		ReadReviewImageUseCaseDto.Output output = readReviewImageUseCase.execute(input);

		// then
		assertThat(output).isNotNull();
		assertThat(output.imageManagerList().size())
			.isEqualTo(2);
		assertThat(output.imageManagerList())
			.isEqualTo(List.of(imageManager1, imageManager2));
		verify(mockReadImageManagerPort, times(1))
			.readIdList(List.of(review1Id, review2Id));
	}

}
