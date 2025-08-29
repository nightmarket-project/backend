package store.nightmarket.application.appitem.usecase;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.nightmarket.application.appitem.fixture.TestDomainFactory;
import store.nightmarket.application.appitem.out.ReadImageManagerPort;
import store.nightmarket.application.appitem.usecase.dto.ReadProductPostImageUseCaseDto;
import store.nightmarket.itemweb.model.ImageManager;
import store.nightmarket.itemweb.state.DomainImageType;

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
		UUID productPostId = UUID.randomUUID();
		ImageManager imageManager = TestDomainFactory.createImageManager(
			UUID.randomUUID(),
			DomainImageType.MAIN,
			1,
			productPostId
		);

		ReadProductPostImageUseCaseDto.Input input = ReadProductPostImageUseCaseDto.Input.builder()
			.id(productPostId)
			.imageTypeList(List.of(DomainImageType.MAIN))
			.build();

		when(mockReadImageManagerPort.readImageTypeList(productPostId, List.of(DomainImageType.MAIN)))
			.thenReturn(List.of(imageManager));

		// when
		ReadProductPostImageUseCaseDto.Output output = readProductPostImageUseCase.execute(input);

		// then
		assertThat(output).isNotNull();
		assertThat(output.imageManagerList())
			.isEqualTo(List.of(imageManager));
		verify(mockReadImageManagerPort, times(1))
			.readImageTypeList(productPostId, List.of(DomainImageType.MAIN));
	}

}
