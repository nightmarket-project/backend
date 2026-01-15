package store.nightmarket.application.appitem.usecase.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.usecase.post.dto.ReadProductPostImageUseCaseDto;
import store.nightmarket.domain.itemweb.model.ImageManager;
import store.nightmarket.domain.itemweb.model.id.ProductPostId;
import store.nightmarket.domain.itemweb.model.state.ImageType;

class ReadProductPostImageUseCaseTest {

	private ReadImageManagerPort mockReadImageManagerPort;
	private ReadProductPostImageUseCase readProductPostImageUseCase;

	@BeforeEach
	void setUp() {
		mockReadImageManagerPort = mock(ReadImageManagerPort.class);
		readProductPostImageUseCase = new ReadProductPostImageUseCase(mockReadImageManagerPort);
	}

	@Test
	@DisplayName("이미지 타입에 맞는 이미지 리스트 읽기")
	void readProductPostImageListByImageType() {
		// given
		ProductPostId productPostId = new ProductPostId(UUID.randomUUID());
		ImageManager imageManager = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			ImageType.MAIN,
			1,
			productPostId.getId()
		);

		ReadProductPostImageUseCaseDto.Input input = ReadProductPostImageUseCaseDto.Input.builder()
			.id(productPostId)
			.imageTypeList(List.of(ImageType.MAIN))
			.build();

		when(mockReadImageManagerPort.readImageTypeList(productPostId, List.of(ImageType.MAIN)))
			.thenReturn(List.of(imageManager));

		// when
		ReadProductPostImageUseCaseDto.Output output = readProductPostImageUseCase.execute(input);

		// then
		assertThat(output).isNotNull();
		assertThat(output.imageManagerList())
			.isEqualTo(List.of(imageManager));
		verify(mockReadImageManagerPort, times(1))
			.readImageTypeList(productPostId, List.of(ImageType.MAIN));
	}

}
